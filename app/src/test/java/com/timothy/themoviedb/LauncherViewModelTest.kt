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

package com.timothy.themoviedb

import com.timothy.themoviedb.core.test.MainDispatcherTest
import com.timothy.themoviedb.ui.LauncherNavigation.Splash
import com.timothy.themoviedb.ui.LauncherViewModel
import com.timothy.themoviedb.ui.ext.value
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class LauncherViewModelTest : MainDispatcherTest() {

    @Test
    fun `viewModel on init should navigate to Splash`() {
        val viewModel: LauncherViewModel

        viewModel = LauncherViewModel()

        viewModel.navigation.value() shouldBeEqualTo Splash
    }
}