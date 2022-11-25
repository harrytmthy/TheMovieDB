/*
 * Copyright 2022 harrytmthy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.timothy.themoviedb.home_impl.domain

import com.timothy.themoviedb.core.usecases.DataFlowUseCase
import com.timothy.themoviedb.home_api.domain.MovieRepository
import com.timothy.themoviedb.home_api.domain.model.MovieDetail
import javax.inject.Inject

class ObserveMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) : DataFlowUseCase<ObserveMovieDetailUseCase.Params, MovieDetail>() {

    override fun execute(params: Params) = repository.loadMovieDetail(params.movieId)

    data class Params(val movieId: Long)
}