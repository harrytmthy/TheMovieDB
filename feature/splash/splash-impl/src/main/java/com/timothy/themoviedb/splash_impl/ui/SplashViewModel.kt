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

package com.timothy.themoviedb.splash_impl.ui

import androidx.lifecycle.viewModelScope
import com.timothy.themoviedb.core.Result.Error
import com.timothy.themoviedb.core.Result.Loading.data
import com.timothy.themoviedb.core.qualifiers.DefaultDispatcher
import com.timothy.themoviedb.splash_api.ui.SplashAction
import com.timothy.themoviedb.splash_impl.ui.SplashNavigation.Home
import com.timothy.themoviedb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val action: SplashAction,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    init {
        getConfig()
    }

    private fun getConfig() {
        viewModelScope.launch {
            action.getConfig().collect { result ->
                when {
                    result.data == true -> navigateToHomeAfterDelay()
                    result is Error -> errorSnackbar(result.message)
                }
            }
        }
    }

    private fun navigateToHomeAfterDelay() {
        viewModelScope.launch(dispatcher) {
            delay(SPLASH_DELAY)
            withContext(Dispatchers.Main) {
                navigateTo(Home)
            }
        }
    }

    companion object {
        private const val SPLASH_DELAY = 1000L
    }
}