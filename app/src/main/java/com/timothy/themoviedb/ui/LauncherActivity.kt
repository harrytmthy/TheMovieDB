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

package com.timothy.themoviedb.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.timothy.themoviedb.splash_api.ui.SplashNavigation
import com.timothy.themoviedb.ui.LauncherNavigation.Splash
import com.timothy.themoviedb.ui.base.BaseActivity
import com.timothy.themoviedb.ui.ext.observeEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LauncherActivity : BaseActivity() {

    private val viewModel by viewModels<LauncherViewModel>()

    @Inject
    lateinit var splashNavigation: SplashNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeNavigation()
    }

    private fun observeNavigation() {
        viewModel.navigation.observeEvent(this) { navigation ->
            if (navigation !is Splash) return@observeEvent
            val intent = splashNavigation.createSplashIntent(this)
            startActivity(intent)
            finishAffinity()
        }
    }
}