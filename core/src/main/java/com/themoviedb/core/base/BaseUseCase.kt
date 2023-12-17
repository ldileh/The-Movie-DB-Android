package com.themoviedb.core.base

import com.themoviedb.core.utils.Resource

@Suppress("unused")
abstract class BaseUseCase {

    suspend fun <T> handleResponse(call: suspend () -> Resource<T>): Resource<T> = call().also { result ->
        // tell use case if token is expired
        if (result.error is Resource.Failure.ErrorHolder.UnAuthorized)
            onTokenExpired()
    }

    protected open fun onTokenExpired(){}
}