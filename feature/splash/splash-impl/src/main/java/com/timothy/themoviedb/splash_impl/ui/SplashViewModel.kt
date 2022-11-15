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

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.timothy.themoviedb.core.Result.Error
import com.timothy.themoviedb.core.Result.Loading.data
import com.timothy.themoviedb.splash_api.ui.SplashAction
import com.timothy.themoviedb.splash_impl.R
import com.timothy.themoviedb.splash_impl.ui.SplashNavigation.Home
import com.timothy.themoviedb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val action: SplashAction
) : BaseViewModel() {

    init {
        getConfig()
    }

    private fun getConfig() {
        viewModelScope.launch {
            action.getConfig().collect { result ->
                when {
                    result.data == true -> navigateTo(Home)
                    result is Error -> errorSnackbar(result.message)
                    else -> errorSnackbar(context.getString(R.string.splash_error_unknown))
                }
            }
        }
    }
}