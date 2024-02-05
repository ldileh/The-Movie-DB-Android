package com.themoviedb.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.themoviedb.core.utils.Resource
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), CoroutineScope{

    private val supervisorJob = SupervisorJob()

    override val coroutineContext: CoroutineContext get() = dispatcher + supervisorJob

    val eventMessage = MutableLiveData<String>()

    /**
     * Handle response from remote data.
     * In this case, handle response code token expired to inform ui
     */
    suspend fun <T> Resource<T>.getResultCase(callback: suspend (result: Resource<T>) -> Unit){
        withContext(Dispatchers.Main){
            // return result
            callback(this@getResultCase)
        }
    }
}