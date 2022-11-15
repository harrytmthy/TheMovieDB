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

import com.timothy.themoviedb.home_api.data.MovieDao
import com.timothy.themoviedb.home_api.data.MovieEntity
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(private val dao: MovieDao) {

    fun getMovies() = dao.getMoviesFlow().distinctUntilChanged()

    suspend fun saveMovies(entities: List<MovieEntity>, page: Int) {
        if (page == 1) {
            dao.deleteThenInsert(entities)
        } else {
            dao.insert(entities)
        }
    }
}