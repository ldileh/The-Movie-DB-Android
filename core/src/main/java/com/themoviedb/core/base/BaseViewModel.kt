package com.themoviedb.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(
    private val dispatcher: CoroutineDispatcher
) : ViewModel(), CoroutineScope{

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        eventMessage.postValue(exception.message)
    }

    val eventMessage = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext get() = dispatcher + exceptionHandler
}