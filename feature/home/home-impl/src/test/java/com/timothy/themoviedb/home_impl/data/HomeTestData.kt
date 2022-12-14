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

import com.timothy.themoviedb.core.paging.PagedData
import com.timothy.themoviedb.home_api.domain.enums.VideoType.YOUTUBE
import com.timothy.themoviedb.home_api.domain.model.Movie
import com.timothy.themoviedb.home_api.domain.model.MovieDetail
import com.timothy.themoviedb.home_api.domain.model.Video
import org.threeten.bp.LocalDate

object HomeTestData {

    private val MOVIE = Movie(
        id = 1L,
        title = "Crayon Bocah",
        overview = "Film spesial untuk bocah.",
        popularity = 1000.0,
        releaseDate = LocalDate.MIN,
        backdropUrl = "",
        posterUrl = ""
    )

    val MOVIES = listOf(MOVIE)

    val PAGED_MOVIES = PagedData(MOVIES, nextPage = 2)

    private val VIDEO = Video(
        name = "White Adam Official Trailer",
        url = "http://dummy.io",
        type = YOUTUBE
    )

    val MOVIE_DETAIL = MovieDetail(movie = MOVIE, videos = listOf(VIDEO))
}