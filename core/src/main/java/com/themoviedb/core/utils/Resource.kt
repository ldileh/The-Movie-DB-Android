package com.themoviedb.core.utils

import com.google.gson.JsonObject

sealed class Resource<T>(val data: T? = null, val error: Failure.ErrorHolder? = null){

    class Success<T>(data: T?) : Resource<T>(data = data)
    class Failure<T>(error: ErrorHolder?): Resource<T>(error = error){

        @Suppress("unused")
        sealed class ErrorHolder(val response: JsonObject? = null, val message: String){

            class NetworkConnection(message: String): ErrorHolder(message = message)

            class BadRequest(response: JsonObject?, message: String): ErrorHolder(
                response, message
            )

            class UnAuthorized(response: JsonObject?, message: String): ErrorHolder(
                response, message
            )

            class InternalServerError(response: JsonObject?, message: String): ErrorHolder(
                response, message
            )

            class ResourceNotFound(message: String): ErrorHolder(message = message)
        }
    }
}