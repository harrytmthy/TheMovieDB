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

import androidx.lifecycle.viewModelScope
import com.timothy.themoviedb.core.Result.Error
import com.timothy.themoviedb.core.Result.Loading
import com.timothy.themoviedb.core.Result.Success
import com.timothy.themoviedb.home_api.HomeAction
import com.timothy.themoviedb.home_impl.ui.HomeViewState.Companion.create
import com.timothy.themoviedb.ui.base.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val action: HomeAction
) : StateViewModel<HomeViewState>(create()) {

    init {
        observeMovies()
        getNextPage()
    }

    private fun observeMovies() {
        viewModelScope.launch {
            action.observeMovies().collect {
                updateViewState { copy(movies = it) }
            }
        }
    }

    fun getNextPage() {
        viewModelScope.launch {
            val nextPage = viewState.value.nextPage
            action.getNextPage(nextPage).collect { result ->
                when (result) {
                    is Loading -> updateViewState {
                        copy(loading = nextPage == 1, loadingNext = nextPage > 1)
                    }
                    is Success -> updateViewState {
                        copy(loading = false, loadingNext = false, nextPage = result.data)
                    }
                    is Error -> {
                        updateViewState { copy(loading = false, loadingNext = false) }
                        errorSnackbar(result.message)
                    }
                }
            }
        }
    }
}