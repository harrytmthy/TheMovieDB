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

package com.timothy.themoviedb.ui.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.timothy.themoviedb.ui.Event
import com.timothy.themoviedb.ui.EventObserver
import com.timothy.themoviedb.ui.state.SnackbarState

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, EventObserver<T>(observer))
}

fun <T> LiveData<Event<T>>.value() = value?.peekContent()

fun LiveData<Event<SnackbarState>>.getSuccessMessage() = value()?.getSuccessMessage()

fun LiveData<Event<SnackbarState>>.getErrorMessage() = value()?.getErrorMessage()