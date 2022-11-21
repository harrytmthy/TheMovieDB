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
import com.timothy.themoviedb.home_api.ui.HomeAction
import com.timothy.themoviedb.home_impl.data.HomeTestData.PAGED_MOVIES
import com.timothy.themoviedb.home_impl.ui.HomeNavigation.MovieDetail
import com.timothy.themoviedb.home_impl.ui.HomeViewState.Companion.create
import com.timothy.themoviedb.ui.ext.getErrorMessage
import com.timothy.themoviedb.ui.ext.value
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
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
        every { action.observeMovies() } returns flowOf(PAGED_MOVIES)

        val viewModel = HomeViewModel(action)

        viewModel.viewState.value.movies shouldBeEqualTo PAGED_MOVIES.data
    }

    @Test
    fun `getNextPage on first page when loading should emit loading state`() {
        every { action.getNextPage(any()) } returns flowOf(Loading)

        val viewModel = HomeViewModel(action)

        viewModel.viewState.value.loading shouldBeEqualTo true
    }

    @Test
    fun `getNextPage on second page when loading should emit loading next state`() {
        givenViewState(initialState = create().copy(nextPage = 2)) {
            every { action.getNextPage(any()) } returns flowOf(Loading)

            val viewModel = HomeViewModel(action)

            viewModel.viewState.value.loadingNext shouldBeEqualTo true
        }
    }

    @Test
    fun `getNextPage when success should set correct data`() {
        every { action.getNextPage(any()) } returns flowOf(Success(Unit))

        val viewModel = HomeViewModel(action)

        viewModel.viewState.value.loading shouldBeEqualTo false
    }

    @Test
    fun `getNextPage on first page when error should not show error snackbar`() {
        every { action.getNextPage(any()) } returns flowOf(Error(message = "Aww snap!"))

        val viewModel = HomeViewModel(action)

        viewModel.snackbarMessage.getErrorMessage() shouldBeEqualTo null
    }

    @Test
    fun `getNextPage on third page when error should show error snackbar`() {
        givenViewState(initialState = create().copy(nextPage = 3)) {
            every { action.getNextPage(any()) } returns flowOf(Error(message = "Aww snap!"))

            val viewModel = HomeViewModel(action)

            viewModel.snackbarMessage.getErrorMessage() shouldBeEqualTo "Aww snap!"
        }
    }

    @Test
    fun `refresh should call getNextPage which loads the first page`() {
        val viewModel = HomeViewModel(action)

        viewModel.refresh()

        verify { action.getNextPage(page = 1) }
        viewModel.viewState.value.refreshing shouldBeEqualTo true
    }

    @Test
    fun `navigateToMovieDetail should navigate to MovieDetail`() {
        val viewModel = HomeViewModel(action)

        viewModel.navigateToMovieDetail(movieId = 123)

        viewModel.navigation.value() shouldBeEqualTo MovieDetail(movieId = 123)
    }

    /**
     * Inject initial values to HomeViewState by stubbing the `create()` function.
     */
    private inline fun givenViewState(
        initialState: HomeViewState,
        crossinline testBlock: () -> Unit
    ) {
        mockkObject(HomeViewState)
        every { create() } returns initialState
        testBlock()
        unmockkObject(HomeViewState)
    }
}