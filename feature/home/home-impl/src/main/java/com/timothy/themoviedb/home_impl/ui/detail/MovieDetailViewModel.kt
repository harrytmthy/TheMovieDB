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
import androidx.lifecycle.viewModelScope
import com.timothy.themoviedb.core.Result.Error
import com.timothy.themoviedb.core.Result.Loading
import com.timothy.themoviedb.core.Result.Success
import com.timothy.themoviedb.home_api.ui.HomeAction
import com.timothy.themoviedb.home_impl.ui.detail.MovieDetailNavigation.Back
import com.timothy.themoviedb.home_impl.ui.detail.MovieDetailNavigation.Video
import com.timothy.themoviedb.home_impl.ui.detail.MovieDetailViewState.Companion.create
import com.timothy.themoviedb.ui.base.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val action: HomeAction,
    savedStateHandle: SavedStateHandle
) : StateViewModel<MovieDetailViewState>(create(savedStateHandle)) {

    init {
        getMovieDetail()
        observeMovieDetail()
    }

    private fun getMovieDetail() {
        viewModelScope.launchWithState {
            if (movieId == MovieDetailViewState.INVALID_MOVIE_ID) {
                navigateTo(Back)
                return@launchWithState
            }
            action.getMovieDetail(movieId).collect { result ->
                when (result) {
                    is Loading -> updateViewState { copy(loading = true, error = false) }
                    is Success -> updateViewState { copy(loading = false, refreshing = false) }
                    is Error -> {
                        updateViewState { copy(loading = false, refreshing = false, error = true) }
                        errorSnackbar(result.message)
                    }
                }
            }
        }
    }

    private fun observeMovieDetail() {
        viewModelScope.launchWithState {
            action.observeMovieDetail(movieId).collect {
                updateViewState { copy(data = it) }
            }
        }
    }

    fun refresh() {
        updateViewState { copy(refreshing = true) }
        getMovieDetail()
    }

    fun navigateToVideo(position: Int) {
        viewModelScope.launchWithState {
            navigateTo(Video(url = data.videos[position].url))
        }
    }
}