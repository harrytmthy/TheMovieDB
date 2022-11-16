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

import com.timothy.themoviedb.core.Result.Error
import com.timothy.themoviedb.core.Result.Success
import com.timothy.themoviedb.core.test.MainDispatcherTest
import com.timothy.themoviedb.splash_api.ui.SplashAction
import com.timothy.themoviedb.splash_impl.ui.SplashNavigation.Home
import com.timothy.themoviedb.ui.ext.getErrorMessage
import com.timothy.themoviedb.ui.ext.value
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class SplashViewModelTest : MainDispatcherTest(testDispatcher = StandardTestDispatcher()) {

    @MockK
    private lateinit var action: SplashAction

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `getConfig when success should navigate to Home`() = runTest {
        every { action.getConfig() } returns flowOf(Success(true))

        val viewModel = SplashViewModel(action, testDispatcher)
        advanceUntilIdle()

        viewModel.navigation.value() shouldBeEqualTo Home
    }

    @Test
    fun `getConfig when error should display snackbar`() = runTest {
        every { action.getConfig() } returns flowOf(Error(message = "Error!"))

        val viewModel = SplashViewModel(action, testDispatcher)
        advanceUntilIdle()

        viewModel.snackbarMessage.getErrorMessage() shouldBeEqualTo "Error!"
    }
}