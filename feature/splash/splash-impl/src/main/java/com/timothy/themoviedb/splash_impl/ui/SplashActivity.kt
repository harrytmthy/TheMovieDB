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

import android.os.Bundle
import androidx.activity.viewModels
import com.timothy.themoviedb.splash_impl.databinding.ActivitySplashBinding
import com.timothy.themoviedb.ui.base.BaseActivity
import com.timothy.themoviedb.ui.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewState()
        observeSnackbarEvent(viewModel.snackbarMessage)
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this) { viewState ->
            if (!viewState.success) return@observe
            viewModel.navigateToHome()
        }
    }
}