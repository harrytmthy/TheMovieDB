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

package com.timothy.themoviedb.splash_impl.data

import com.timothy.themoviedb.splash_api.data.ConfigEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigResponse(
    @SerialName("base_url") val baseUrl: String?,
    @SerialName("secure_base_url") val secureUrl: String?,
    @SerialName("backdrop_sizes") val backdropSizes: List<String>?,
    @SerialName("poster_sizes") val posterSizes: List<String>?
) {

    fun toEntity() = ConfigEntity(
        baseUrl = baseUrl.orEmpty(),
        secureUrl = secureUrl.orEmpty(),
        backdropSizes = emptyList(),
        posterSizes = emptyList()
    )
}