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

package com.timothy.themoviedb.home_impl.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.timothy.themoviedb.home_api.domain.model.MovieDetail
import com.timothy.themoviedb.ui.base.ViewState

data class MovieDetailViewState(
    val movieId: Long,
    val loading: Boolean,
    val refreshing: Boolean,
    val error: Boolean,
    val data: MovieDetail
) : ViewState() {

    companion object {

        internal const val KEY_MOVIE_ID = "movieId"

        internal const val INVALID_MOVIE_ID = 1L

        fun create(savedStateHandle: SavedStateHandle) = MovieDetailViewState(
            movieId = savedStateHandle[KEY_MOVIE_ID] ?: INVALID_MOVIE_ID,
            loading = false,
            refreshing = false,
            error = false,
            data = MovieDetail.EMPTY
        )
    }
}