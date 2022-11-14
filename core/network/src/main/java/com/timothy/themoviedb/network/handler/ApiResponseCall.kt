/*
 * Copyright 2022 harrytmthy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.timothy.themoviedb.network.handler

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.timothy.themoviedb.network.NetworkStateObserver
import com.timothy.themoviedb.network.handler.ApiResponse.ApiError
import com.timothy.themoviedb.network.handler.ApiResponse.NetworkError
import com.timothy.themoviedb.network.handler.ApiResponse.NoInternetError
import com.timothy.themoviedb.network.handler.ApiResponse.Success
import com.timothy.themoviedb.network.handler.ApiResponse.UnknownError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

internal class ApiResponseCall<T : Any>(
    private val delegate: Call<T>,
    private val networkStateObserver: NetworkStateObserver
) : Call<ApiResponse<T>> {

    override fun enqueue(callback: Callback<ApiResponse<T>>) = delegate.enqueue(
        object : Callback<T> {
            override fun onResponse(responseCall: Call<T>, response: Response<T>) {
                val apiResponse = getApiResponse(response)
                callback.onResponse(this@ApiResponseCall, Response.success(apiResponse))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val response = when {
                    !networkStateObserver.hasInternetConnection() -> NoInternetError
                    t is IOException -> NetworkError(t)
                    else -> UnknownError
                }
                callback.onResponse(this@ApiResponseCall, Response.success(response))
            }
        }
    )

    private fun getApiResponse(response: Response<T>): ApiResponse<T> = when {
        !networkStateObserver.hasInternetConnection() -> NoInternetError
        response.isSuccessful -> response.body()?.let {
            when (val code = response.code()) {
                in 200..299 -> Success(it)
                in 400..409 -> ApiError(response.message(), code)
                else -> UnknownError
            }
        } ?: UnknownError
        else -> response.errorBody()?.let {
            val jsonObj = Gson().fromJson(it.string(), JsonObject::class.java)
            ApiError(jsonObj.get("message").asString, response.code())
        } ?: UnknownError
    }

    override fun clone() = ApiResponseCall(delegate.clone(), networkStateObserver)

    override fun execute() = Response.success(getApiResponse(delegate.execute()))

    override fun isExecuted() = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled() = delegate.isCanceled

    override fun request() = delegate.request()

    override fun timeout() = delegate.timeout()
}