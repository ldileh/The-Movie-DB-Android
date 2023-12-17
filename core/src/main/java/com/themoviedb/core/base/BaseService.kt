package com.themoviedb.core.base

import com.themoviedb.core.utils.Resource
import com.themoviedb.core.utils.RetrofitConfig
import com.themoviedb.core.utils.ext.logError
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


abstract class BaseService {

    companion object{

        fun <T> createService(serviceClass: Class<T>, url: String, isDebug: Boolean): T = RetrofitConfig
            .getRetrofitBuilder(url, isDebug)
            .create(serviceClass)
    }

    protected suspend fun <T> getResult(
        call: suspend () -> Response<T>
    ): Resource<T> = call().let { response ->
        try {
            if(response.isSuccessful)
                Resource.Success(response.body())
            else
                responseError(
                    exception = HttpException(response),
                    message = response.message(),
                    code = response.code(),
                    response = response
                )
        } catch (e: Exception) {
            // close request
            response.errorBody()?.close()

            // return response error to ui
            responseError(
                exception = e,
                message = e.message ?: e.toString(),
                response = response
            )
        }
    }

    private fun <T> responseError(
        message: String,
        code: Int? = null,
        exception: Exception? = null,
        response: Response<T>? = null
    ): Resource<T> {
        val tmpMsg = message.ifEmpty { "Something went wrong!" }

        logError(tmpMsg, exception)

        return Resource.Failure(when(exception){
            is IOException ->
                Resource.Failure.ErrorHolder.NetworkConnection("No internet connection")

            else -> exception.handleResponseError(code, tmpMsg, response)
        })
    }

    /**
     * handle response error from body request data.
     * just add some http code on case HttpException
     */
    private fun <T> Exception?.handleResponseError(
        code: Int?,
        message: String,
        response: Response<T>?
    ): Resource.Failure.ErrorHolder = when(this){
        is HttpException -> when(code){
            401 -> Resource.Failure.ErrorHolder.UnAuthorized(
                response.getBodyRequestError(), message
            )

            else -> Resource.Failure.ErrorHolder.InternalServerError(
                response.getBodyRequestError(), message
            )
        }

        else -> Resource.Failure.ErrorHolder.InternalServerError(
            response.getBodyRequestError(), message
        )
    }

    private fun <T> Response<T>?.getBodyRequestError(): JsonObject? {
        return try {
            val errorBody = this?.errorBody()?.string() ?: return null

            JsonParser().parse(errorBody).asJsonObject
        } catch (e: java.lang.Exception) {
            null
        }
    }
}