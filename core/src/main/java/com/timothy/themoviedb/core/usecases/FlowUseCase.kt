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

package com.timothy.themoviedb.core.usecases

import com.timothy.themoviedb.core.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
 * A use case which simply runs on the assigned [dispatcher] and returns a [Result].
 */
abstract class FlowUseCase<in P, R>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(params: P): Flow<Result<R>> = execute(params)
        .catch { e -> emit(Result.Error(message = e.message.orEmpty(), error = e)) }
        .flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<Result<R>>
}

operator fun <T> FlowUseCase<Unit, T>.invoke() = invoke(Unit)