/*
 * Copyright 2022 harrytmthy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.timothy.themoviedb.core.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

/**
 * A use case which runs on the assigned [dispatcher] and returns a data.
 * Use this only to query data from local data sources (otherwise, please use FlowUseCase).
 */
abstract class DataFlowUseCase<in P, R>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(params: P) = execute(params)
        .catch { e -> Timber.e(e) }
        .flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<R>
}