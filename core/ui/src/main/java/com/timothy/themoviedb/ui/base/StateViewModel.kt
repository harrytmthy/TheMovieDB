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

package com.timothy.themoviedb.ui.base

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class StateViewModel<S : ViewState>(initialState: S) : BaseViewModel() {

    private val _viewState = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()

    fun getViewState() = _viewState.asLiveData()

    /**
     * Updates ViewState instance using [reducer] block to facilitate `copy()`. It is launched on
     * the Default Dispatcher to ensure concurrent updates do not block the Main Thread.
     *
     * Usage:
     * `updateViewState { copy(loading = true, error = false) }`
     */
    protected fun updateViewState(reducer: S.() -> S) {
        viewModelScope.launch {
            _viewState.update(reducer)
        }
    }

    /**
     * An extension functions to omit redundant chain-calls of `viewState.value`.
     *
     * Although this seems convenient, beware of possible data integrity issues:
     * - Any changes towards the ViewState does not reflected inside the [block].
     * - When collecting data (inside `flow.collect { }`), NEVER read ViewState from [block].
     *   Instead, use StateViewModel's exposed `viewState` StateFlow to get the latest state.
     */
    protected inline fun CoroutineScope.launchWithState(
        context: CoroutineContext = EmptyCoroutineContext,
        crossinline block: suspend S.() -> Unit
    ) = launch(context) { block(viewState.value) }
}