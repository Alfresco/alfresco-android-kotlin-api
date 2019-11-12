package com.alfresco.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.SupervisorJob

/**
 *
 * Created by Bogdan Roatis on 25 August 2019.
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * Private mutable implementation that should be used inside the viewmodel
     * as opposed to [isLoading] which is the one that is being observed from the outside
     */
    protected val _isLoading = MutableLiveData<Boolean>()

    /**
     * Used to send the loading status of the operation.
     * The loading status is true when loading and false otherwise
     */
    val isLoading: LiveData<Boolean> get() = _isLoading

    /**
     * The supervisor job for all the coroutines
     */
    protected val job = SupervisorJob()
}
