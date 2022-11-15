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

import com.timothy.themoviedb.core.Result.Error
import com.timothy.themoviedb.core.Result.Loading
import com.timothy.themoviedb.core.Result.Success
import com.timothy.themoviedb.core.test.MainDispatcherTest
import com.timothy.themoviedb.home_api.HomeAction
import com.timothy.themoviedb.home_impl.data.HomeTestData.MOVIES
import com.timothy.themoviedb.ui.ext.getErrorMessage
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class HomeViewModelTest : MainDispatcherTest() {

    @RelaxedMockK
    private lateinit var action: HomeAction

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `observeMovies should set correct data`() {
        every { action.observeMovies() } returns flowOf(MOVIES)

        val viewModel = HomeViewModel(action)

        viewModel.viewState.value.movies shouldBeEqualTo MOVIES
    }

    @Test
    fun `getNextPage when loading should emit loading state`() {
        every { action.getNextPage(any()) } returns flowOf(Loading)

        val viewModel = HomeViewModel(action)

        viewModel.viewState.value.loading shouldBeEqualTo true
    }

    @Test
    fun `getNextPage when success should set correct data`() {
        every { action.getNextPage(any()) } returns flowOf(Success(data = 2))

        val viewModel = HomeViewModel(action)

        viewModel.viewState.value.nextPage shouldBeEqualTo 2
    }

    @Test
    fun `getNextPage when error should show error snackbar`() {
        every { action.getNextPage(any()) } returns flowOf(Error(message = "Aww snap!"))

        val viewModel = HomeViewModel(action)

        viewModel.snackbarMessage.getErrorMessage() shouldBeEqualTo "Aww snap!"
    }
}