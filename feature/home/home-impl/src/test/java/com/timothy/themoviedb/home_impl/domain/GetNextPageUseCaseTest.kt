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

package com.timothy.themoviedb.home_impl.domain

import com.timothy.themoviedb.core.Result.Success
import com.timothy.themoviedb.home_api.domain.MovieRepository
import com.timothy.themoviedb.home_impl.domain.GetNextPageUseCase.Params
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class GetNextPageUseCaseTest {

    @Test
    fun `invoke should return correct result`() = runTest {
        val repository = mockk<MovieRepository>()
        every { repository.getNextPage(any()) } returns flowOf(Unit)

        val result = GetNextPageUseCase(repository).invoke(Params(page = 1)).last()

        result shouldBeEqualTo Success(Unit)
    }
}