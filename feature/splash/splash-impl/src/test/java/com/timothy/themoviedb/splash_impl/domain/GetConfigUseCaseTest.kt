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

package com.timothy.themoviedb.splash_impl.domain

import com.timothy.themoviedb.core.Result.Loading.data
import com.timothy.themoviedb.core.ext.invoke
import com.timothy.themoviedb.splash_api.domain.ConfigRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class GetConfigUseCaseTest {

    @Test
    fun `invoke should return correct result`() = runTest {
        val repository = mockk<ConfigRepository>()
        every { repository.getConfig() } returns flowOf(true)

        val result = GetConfigUseCase(repository).invoke().last()

        result.data shouldBeEqualTo true
    }
}