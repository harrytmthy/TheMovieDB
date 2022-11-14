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

import com.timothy.themoviedb.core.exceptions.ApiErrorException
import com.timothy.themoviedb.core.exceptions.NoInternetException
import com.timothy.themoviedb.core.exceptions.UnknownErrorException
import java.io.IOException

/**
 * A custom API response wrapper which introduces four different states:
 * 1. [Success] -> The response contains a body which deserialized into [data].
 * 2. [ApiError] -> Represents non-2xx responses with an error message and status code.
 * 3. [NetworkError] -> Represents network failures.
 * 4. [NoInternetError] -> Represent error due to no internet connection.
 * 4. [UnknownError] -> Represents unexpected exceptions during request or response creation.
 *
 * Guidelines:
 * Always use sealed class to represent states. This will support the type checks and prevent any
 * exhaustive [when] expression (which will be banned at Kotlin 1.7).
 */
sealed class ApiResponse<out T : Any> {

    data class Success<T : Any>(val data: T) : ApiResponse<T>()

    data class ApiError(val message: String, val code: Int) : ApiResponse<Nothing>()

    data class NetworkError(val error: IOException) : ApiResponse<Nothing>()

    object NoInternetError : ApiResponse<Nothing>()

    object UnknownError : ApiResponse<Nothing>()

    fun unwrap() = when (this) {
        is Success -> data
        is NoInternetError -> throw NoInternetException
        is ApiError -> throw ApiErrorException(message = message, code = code)
        is NetworkError -> throw error
        is UnknownError -> throw UnknownErrorException
    }
}