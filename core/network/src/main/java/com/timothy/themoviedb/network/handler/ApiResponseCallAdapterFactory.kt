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

import com.timothy.themoviedb.network.NetworkStateObserver
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseCallAdapterFactory(
    private val networkStateObserver: NetworkStateObserver
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) return null
        check(returnType is ParameterizedType)

        val responseType = getParameterUpperBound(FIRST_INDEX, returnType)
        if (getRawType(responseType) != ApiResponse::class.java) return null
        check(responseType is ParameterizedType)

        val successType = getParameterUpperBound(FIRST_INDEX, responseType)
        return ApiResponseCallAdapter<Any>(successType, networkStateObserver)
    }

    companion object {
        private const val FIRST_INDEX = 0
    }
}