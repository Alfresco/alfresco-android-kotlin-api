package com.alfresco.core.data

/**
 * Entity used to store the result of an operation
 *
 * Created by Bogdan Roatis on 12 August 2019.
 */
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
sealed class Result<out D, out E : Exception> {

    /**
     * Class used to represent a success outcome
     */
    @Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
    class Success<out D>(val data: D) : Result<D, Nothing>()

    /**
     * Class used to represent a failed outcome
     */
    @Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
    class Error<out E : Exception>(val exception: E) : Result<Nothing, E>()

    /**
     * Class used to represent a state for the incoming result
     */
    sealed class State : Result<Nothing, Nothing>() {
        object Loading : State()
        object Loaded : State()
    }

    /**
     * Returns `true` if this instance represents successful outcome.
     * In this case [isFailure] returns `false`.
     */
    val isSuccess: Boolean get() = this !is Error

    /**
     * Returns `true` if this instance represents failed outcome.
     * In this case [isSuccess] returns `false`.
     */
    val isFailure: Boolean get() = this is Error

    /**
     * Handles result based on the type of the [Result]
     *
     * @param successAction the action to be called if the [Result] is [Result.Success]
     * @param errorAction the action to be called if the [Result] is [Result.Error]
     * @param stateAction the action to be called if the [Result] is [Result.State.Loading] or [Result.State.Loaded]
     */
    fun handleResult(
            successAction: (D) -> Unit = {},
            errorAction: (Error<Exception>) -> Unit = {},
            stateAction: (State) -> Unit = {}) {

        when (this) {
            is Success -> successAction(data)
            is Error -> errorAction(this)
            is State -> stateAction(this)
        }
    }

    /**
     * Handles success result only if the type of the [Result] is [Result.Success]
     */
    suspend fun <R> onResult(
            successAction: suspend (D) -> R,
            errorAction: suspend (E: Exception) -> R,
            loadingAction: suspend (State) -> R): R {
        return when (this) {
            is Success -> successAction(data)
            is Error -> errorAction(exception)
            is State -> loadingAction(this)
        }
    }

    /**
     * Handles success result only if the type of the [Result] is [Result.Success]
     */
    suspend fun onSuccess(successAction: suspend (D) -> Unit) {
        when (this) {
            is Success -> successAction(data)
        }
    }

    /**
     * Handles error result only if the type of the [Result] is [Result.Error]
     */
    suspend fun onError(errorAction: suspend (E) -> Unit) {
        when (this) {
            is Error -> errorAction(exception)
        }
    }

    companion object {
        inline fun <D> of(function: () -> D): Result<D, Exception> {
            return try {
                success(function())
            } catch (exception: Exception) {
                error(exception)
            }
        }

        fun <D, E : Exception> from(data: D, exception: E): Result<D, E> {
            return if (data != null) {
                success(data)
            } else {
                error(exception)
            }
        }

        /**
         * Returns an instance that encapsulates the given [value] as successful value.
         */
        fun <T> success(value: T) =
                Success(value)

        /**
         * Returns an instance that encapsulates the given [exception] as failure.
         */
        fun <E : Exception> error(exception: E) =
                Error(exception)

        fun loading() = State.Loading

        fun loaded() = State.Loaded
    }

    inline fun <D2> map(transform: D.() -> D2): Result<D2, E> {
        return when (this) {
            is Error -> {
                Error(exception)
            }
            is Success -> {
                Success(transform(data))
            }

            else -> throw UnknownTypeOfResultException()
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is State.Loaded -> "State[Loaded]"
            is State.Loading -> "State[Loading]"
        }
    }
}

class UnknownTypeOfResultException : Exception()
