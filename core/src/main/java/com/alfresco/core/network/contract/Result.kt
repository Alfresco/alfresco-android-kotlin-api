package com.alfresco.core.network.contract

/**
 * Created by Bogdan Roatis on 3/6/2019.
 */
sealed class Result<out D, out E : Throwable> {
    class Success<out D>(val data: D) : Result<D, Nothing>()
    class Failure<out E : Throwable>(val error: E) : Result<Nothing, E>()

    sealed class State : Result<Nothing, Nothing>() {
        class Loading : State()
        class Loaded : State()
    }

    companion object {
        inline fun <D> of(function: () -> D): Result<D, Throwable> {
            return try {
                Success(function.invoke())
            } catch (exception: Exception) {
                Failure(exception)
            }
        }

        fun <D, E : Throwable> from(data: D, error: E): Result<D, E> {
            return if (data != null) {
                Success(data)
            } else {
                Failure(error)
            }
        }
    }

    fun <D2> mapDataTo(function: D.() -> D2): Result<D2, E> {
        return when (this) {
            is Failure -> {
                Failure(error = error)
            }
            is Success -> {
                Success(function(data))
            }
            else -> throw UnknownError()
        }
    }

    fun handleResult(successBlock: (D) -> Unit = {}, failureBlock: (E) -> Unit = {}, stateBlock: (State) -> Unit = {}) {
        when (this) {
            is Success -> successBlock(data)
            is Failure -> failureBlock(error)
            is State -> stateBlock(this)
        }
    }

    suspend fun handleSuspendResult(failureBlock: suspend (E) -> Unit = {}, stateBlock: suspend (State) -> Unit = {}, successBlock: suspend (D) -> Unit = {}) {
        when (this) {
            is Success -> successBlock(data)
            is Failure -> failureBlock(error)
            is State -> stateBlock(this)
        }
    }

    suspend fun handleSuccess(successBlock: suspend (D) -> Unit = {}) {
        when (this) {
            is Success -> successBlock(data)
        }
    }

    suspend fun handleFailure(failureBlock: suspend (E) -> Unit = {}) {
        when (this) {
            is Failure -> failureBlock(error)
        }
    }
}
