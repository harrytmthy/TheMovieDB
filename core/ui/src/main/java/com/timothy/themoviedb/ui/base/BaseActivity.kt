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

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.timothy.themoviedb.ui.Event
import com.timothy.themoviedb.ui.ext.observeEvent
import com.timothy.themoviedb.ui.state.SnackbarState
import com.timothy.themoviedb.ui.state.SnackbarState.Error
import com.timothy.themoviedb.ui.state.SnackbarState.Success
import com.timothy.themoviedb.ui.util.SnackbarUtil.showErrorSnackBar
import com.timothy.themoviedb.ui.util.SnackbarUtil.showSuccessSnackbar

abstract class BaseActivity : AppCompatActivity() {

    protected fun observeSnackbarEvent(snackBarEvent: LiveData<Event<SnackbarState>>) {
        snackBarEvent.observeEvent(this) { snackbarState ->
            when (snackbarState) {
                is Success -> showSuccessSnackbar(
                    view = window?.decorView?.rootView ?: return@observeEvent,
                    message = snackbarState.message
                )
                is Error -> showErrorSnackBar(
                    view = window?.decorView?.rootView ?: return@observeEvent,
                    message = snackbarState.message
                )
            }
        }
    }
}