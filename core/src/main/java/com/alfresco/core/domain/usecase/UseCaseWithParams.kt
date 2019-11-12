package com.alfresco.core.domain.usecase

import com.alfresco.core.data.Result
import com.alfresco.core.extension.runSafe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *
 * Created by Bogdan Roatis on 25 August 2019.
 */
abstract class UseCaseWithParams<in Params, T> : UseCase() {

    protected abstract suspend fun execute(params: Params): T

    /**
     * Generates a flow containing all the [Result] states
     */
    private fun generateFlow(params: Params) = flow {
        // to notify that the operation of has started,
        // send the loading state through the channel
        emit(Result.loading())

        try {
            // try to execute with the given params
            emit(Result.success(execute(params)))
        } catch (e: Exception) {
            emit(Result.error(e))
        }

        // to notify that the operation of has ended,
        // send the loaded state through the channel
        emit(Result.loaded())
    }

    /**
     * Convenient method for listening and consuming every [Result] from
     * [UseCaseWithParams]'s [Flow]
     *
     * @param params the params needed for this function to execute
     * @param stateBlock called when the [Result] is [Result.State]
     * @param errorBlock called when the [Result] is [Result.Error]
     * @param successBlock called when the [Result] is [Result.Success]
     */
    suspend fun executeAndCollect(
            params: Params,
            stateBlock: (Result.State) -> Unit,
            errorBlock: (Result.Error<Exception>) -> Unit,
            successBlock: (T) -> Unit) {

        generateFlow(params).runSafe { it.handleResult(successBlock, errorBlock, stateBlock) }
    }
}
