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

package com.timothy.themoviedb.core.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule

/**
 * A base Unit Test class which enforces some rules that keep all tests running on Dispatchers.Main.
 * Please use either `StandardTestDispatcher` or `UnconfinedTestDispatcher`.
 */
abstract class MainDispatcherTest(dispatcher: TestDispatcher = UnconfinedTestDispatcher()) {

    /**
     * Executes Architecture Components tasks in the same thread.
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * Overrides Coroutines Dispatchers.Main.
     */
    @get:Rule
    val coroutineRule = MainCoroutineRule(dispatcher)
}