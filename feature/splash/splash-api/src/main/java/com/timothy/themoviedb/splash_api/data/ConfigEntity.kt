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

package com.timothy.themoviedb.splash_api.data

import androidx.room.Entity
import androidx.room.Fts4

@Entity
@Fts4
data class ConfigEntity(
    val baseUrl: String,
    val secureUrl: String,
    val backdropSizes: List<String>,
    val posterSizes: List<String>
) {

    fun getBackdropUrl(backdropPath: String) = getImageUrl(
        path = backdropPath,
        size = backdropSizes[backdropSizes.size / 2]
    )

    fun getPosterUrl(posterPath: String) = getImageUrl(
        path = posterPath,
        size = posterSizes[posterSizes.size / 2]
    )

    private fun getImageUrl(path: String, size: String): String {
        val url = secureUrl.ifBlank { baseUrl }
        return "$url$size$path"
    }
}