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

package com.timothy.themoviedb.home_impl.data

import com.timothy.themoviedb.home_api.data.MovieEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val id: Long?,
    val title: String?,
    val overview: String?,
    val popularity: Double?,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("poster_path") val posterPath: String?
) {

    fun toEntity() = MovieEntity(
        id = id ?: 0L,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0.0,
        releaseDate = releaseDate.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        posterPath = posterPath.orEmpty(),
    )
}