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

import com.timothy.themoviedb.core.paging.PagedData
import com.timothy.themoviedb.home_api.domain.model.Movie
import com.timothy.themoviedb.home_api.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getNextPage(page: Int): Flow<Unit>
    fun loadMovies(): Flow<PagedData<Movie>>
    fun getMovieDetail(movieId: Long): Flow<Unit>
    fun loadMovieDetail(movieId: Long): Flow<MovieDetail>
}