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

package com.timothy.themoviedb.home_impl.ui

import com.timothy.themoviedb.home_api.domain.Movie
import com.timothy.themoviedb.ui.base.ViewState

data class HomeViewState(
    val loading: Boolean,
    val loadingNext: Boolean,
    val nextPage: Int,
    val movies: List<Movie>,
) : ViewState() {

    companion object {
        fun create() = HomeViewState(
            loading = false,
            loadingNext = false,
            nextPage = 1,
            movies = emptyList()
        )
    }
}