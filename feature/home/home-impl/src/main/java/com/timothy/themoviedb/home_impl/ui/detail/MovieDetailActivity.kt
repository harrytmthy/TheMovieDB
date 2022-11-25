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

package com.timothy.themoviedb.home_impl.ui.detail

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.timothy.themoviedb.home_api.domain.model.MovieDetail
import com.timothy.themoviedb.home_impl.R
import com.timothy.themoviedb.home_impl.databinding.ActivityMovieDetailBinding
import com.timothy.themoviedb.home_impl.ui.detail.MovieDetailNavigation.Back
import com.timothy.themoviedb.ui.base.BaseActivity
import com.timothy.themoviedb.ui.base.Navigation
import com.timothy.themoviedb.ui.ext.viewBinding
import com.timothy.themoviedb.ui.handler.ViewStateHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : BaseActivity() {

    override val binding by viewBinding(ActivityMovieDetailBinding::inflate)

    override val viewModel by viewModels<MovieDetailViewModel>()

    override val viewStateHandler = object : ViewStateHandler<MovieDetailViewState> {
        override fun onViewStateUpdated(viewState: MovieDetailViewState) {
            when {
                viewState.loading -> renderLoadingState()
                viewState.error -> renderErrorState()
                else -> renderSuccessState(viewState.data)
            }
        }
    }

    override fun setupUi() {
        with(binding) {
            toolbar.bind(this@MovieDetailActivity, R.string.movie_detail_title)
            srlContent.setOnRefreshListener(viewModel::refresh)
        }
    }

    private fun renderLoadingState() {
        // TODO: Will continue in the next commit
    }

    private fun renderSuccessState(data: MovieDetail) {
        // TODO: Will continue in the next commit
    }

    private fun renderErrorState() {
        // TODO: Will continue in the next commit
    }

    override fun onNavigate(navigation: Navigation) {
        when (navigation) {
            is Back -> onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        fun createIntent(context: Context, movieId: Long): Intent {
            return Intent(context, MovieDetailActivity::class.java).putExtras(
                bundleOf(MovieDetailViewState.KEY_MOVIE_ID to movieId)
            )
        }
    }
}