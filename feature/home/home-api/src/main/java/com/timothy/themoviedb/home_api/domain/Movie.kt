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

package com.timothy.themoviedb.home_api.domain

import com.timothy.themoviedb.core.utils.LocalDateTimeUtil.getLocalDate
import com.timothy.themoviedb.home_api.data.MovieEntity
import org.threeten.bp.LocalDate

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val popularity: Double,
    val releaseDate: LocalDate,
    val backdropUrl: String,
    val posterUrl: String
) {

    companion object {
        fun from(entity: MovieEntity, backdropUrl: String, posterUrl: String) = Movie(
            id = entity.id,
            title = entity.title,
            overview = entity.overview,
            popularity = entity.popularity,
            releaseDate = getLocalDate(entity.releaseDate),
            backdropUrl = backdropUrl,
            posterUrl = posterUrl
        )
    }
}