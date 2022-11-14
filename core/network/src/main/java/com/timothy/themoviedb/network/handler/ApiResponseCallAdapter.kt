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
import java.lang.reflect.Type

class ApiResponseCallAdapter <T : Any>(
    private val successType: Type,
    private val networkStateObserver: NetworkStateObserver
) : CallAdapter<T, Call<ApiResponse<T>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<ApiResponse<T>> {
        return ApiResponseCall(call, networkStateObserver)
    }
}