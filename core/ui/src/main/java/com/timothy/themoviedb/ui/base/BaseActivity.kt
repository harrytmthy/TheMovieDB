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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.timothy.themoviedb.ui.ext.observeEvent
import com.timothy.themoviedb.ui.handler.DefaultViewStateHandler
import com.timothy.themoviedb.ui.handler.ViewStateHandler
import com.timothy.themoviedb.ui.state.SnackbarState.Error
import com.timothy.themoviedb.ui.state.SnackbarState.Success
import com.timothy.themoviedb.ui.util.SnackbarUtil.showErrorSnackBar
import com.timothy.themoviedb.ui.util.SnackbarUtil.showSuccessSnackbar

/**
 * An AppCompatActivity that contains both [binding] and [viewModel].
 * Consider using this to reduce boilerplate in Activities.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected abstract val binding: ViewBinding

    protected abstract val viewModel: BaseViewModel

    protected open val viewStateHandler: ViewStateHandler<ViewState> = DefaultViewStateHandler

    protected abstract fun setupUi()

    protected abstract fun onNavigate(navigation: Navigation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUi()
        observeSnackbarEvent()
        observeViewState()
        observeNavigation()
    }

    protected open fun getSnackbarAnchorView() = binding.root

    private fun observeSnackbarEvent() {
        viewModel.snackbarMessage.observeEvent(this) { snackbarState ->
            when (snackbarState) {
                is Success -> showSuccessSnackbar(
                    view = getSnackbarAnchorView(),
                    message = snackbarState.message
                )
                is Error -> showErrorSnackBar(
                    view = getSnackbarAnchorView(),
                    message = snackbarState.message
                )
            }
        }
    }

    private fun observeViewState() = (viewModel as? StateViewModel<*>)
        ?.getViewState()
        ?.observe(this, viewStateHandler::onViewStateUpdated)

    private fun observeNavigation() = viewModel.navigation.observeEvent(this, ::onNavigate)
}