package com.alfresco.core.domain.usecase

import com.alfresco.core.data.Result
import com.alfresco.core.extension.runSafe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *
 * Created by Bogdan Roatis on 25 August 2019.
 */
abstract class UseCaseWithoutParams<T> : UseCase() {

    protected abstract suspend fun execute(): T

    /**
     * Generates a flow containing all the [Result] states
     */
    private fun generateFlow() = flow {
        // to notify that the operation of has started,
        // send the loading state through the channel
        emit(Result.loading())

        try {
            // try to execute with the given params
            emit(Result.success(execute()))
        } catch (e: Exception) {
            emit(Result.error(e))
        }

        // to notify that the operation of has ended,
        // send the loaded state through the channel
        emit(Result.loaded())
    }

    /**
     * Convenient method for listening and consuming every [Result] from
     * [UseCaseWithoutParams]'s [Flow]
     *
     * @param stateBlock called when the [Result] is [Result.State]
     * @param errorBlock called when the [Result] is [Result.Error]
     * @param successBlock called when the [Result] is [Result.Success]
     */
    suspend fun executeAndCollect(
            stateBlock: (Result.State) -> Unit,
            errorBlock: (Result.Error<Exception>) -> Unit,
            successBlock: (T) -> Unit) {

        generateFlow().runSafe { it.handleResult(successBlock, errorBlock, stateBlock) }
    }
}
