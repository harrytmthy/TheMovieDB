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

import com.timothy.themoviedb.common.layout.emptyLayout
import com.timothy.themoviedb.common.layout.loadingLayout
import com.timothy.themoviedb.ui.base.BaseEpoxyController

class HomeEpoxyController(
    private val onItemClick: (movieId: Int) -> Unit
) : BaseEpoxyController<HomeViewState>() {

    override fun buildModels(state: HomeViewState) {
        when {
            state.loading -> renderLoadingState()
            state.movies.isEmpty() -> renderEmptyState()
        }
    }

    private fun renderLoadingState() = loadingLayout { id("home-loading-layout") }

    private fun renderEmptyState() = emptyLayout { id("home-empty-layout") }
}