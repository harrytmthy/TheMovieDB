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

package com.timothy.themoviedb.home_impl.ui

import androidx.activity.viewModels
import com.timothy.themoviedb.common.ext.addLoadMoreListener
import com.timothy.themoviedb.home_impl.R
import com.timothy.themoviedb.home_impl.databinding.ActivityHomeBinding
import com.timothy.themoviedb.home_impl.ui.HomeNavigation.MovieDetail
import com.timothy.themoviedb.home_impl.ui.detail.MovieDetailActivity.Companion.createIntent
import com.timothy.themoviedb.ui.base.BaseActivity
import com.timothy.themoviedb.ui.base.Navigation
import com.timothy.themoviedb.ui.ext.viewBinding
import com.timothy.themoviedb.ui.handler.ViewStateHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    override val binding by viewBinding(ActivityHomeBinding::inflate)

    override val viewModel by viewModels<HomeViewModel>()

    private val controller by lazy { HomeEpoxyController(viewModel::navigateToMovieDetail) }

    override val viewStateHandler = object : ViewStateHandler<HomeViewState> {
        override fun onViewStateUpdated(viewState: HomeViewState) {
            binding.srlContent.isEnabled = !viewState.loading
            binding.srlContent.isRefreshing = viewState.refreshing
            controller.setData(viewState)
        }
    }

    override fun setupUi() {
        binding.toolbar.bind(this, R.string.home_title)
        binding.srlContent.setOnRefreshListener(viewModel::refresh)
        binding.rvContent.addLoadMoreListener(
            canLoadMore = { viewModel.viewState.value.canLoadMore() },
            onLoadMore = viewModel::getNextPage
        )
        binding.rvContent.setController(controller)
    }

    override fun onNavigate(navigation: Navigation) {
        when (navigation) {
            is MovieDetail -> startActivity(createIntent(this, navigation.movieId))
        }
    }
}