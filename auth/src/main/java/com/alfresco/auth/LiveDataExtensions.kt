package com.alfresco.auth

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by Bogdan Roatis on 8/2/2019.
 */
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) =
        liveData.observe(this, Observer(body))

fun <L : LiveData<Throwable>> LifecycleOwner.failure(liveData: L, body: (Throwable?) -> Unit) =
        liveData.observe(this, Observer(body))
