package com.alfresco.core.extension

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import com.alfresco.core.data.Result

/**
 *
 * Created by Bogdan Roatis on 08 September 2019.
 */

/**
 * Convenient function for handling the [flowOn] computation.
 * Runs the main process of the flow on the [Dispatchers.IO]
 * and for each value it calls the [onEachBlock] which runs
 * on [Dispatchers.Main]
 */
suspend fun <T> Flow<Result<T, Exception>>.runSafe(
        onEachBlock: (Result<T, Exception>) -> Unit) {

    flowOn(Dispatchers.IO)
            .onEach { onEachBlock(it) }
            .catch {}
            .flowOn(Dispatchers.Main)
            .collect()
}
