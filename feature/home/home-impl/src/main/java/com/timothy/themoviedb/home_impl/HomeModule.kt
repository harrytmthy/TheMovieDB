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

package com.timothy.themoviedb.home_impl

import android.content.Context
import android.content.Intent
import com.timothy.themoviedb.core.ext.invoke
import com.timothy.themoviedb.home_api.domain.MovieRepository
import com.timothy.themoviedb.home_api.ui.HomeAction
import com.timothy.themoviedb.home_api.ui.HomeDestination
import com.timothy.themoviedb.home_impl.data.MovieRepositoryImpl
import com.timothy.themoviedb.home_impl.data.MovieService
import com.timothy.themoviedb.home_impl.domain.GetMovieDetailUseCase
import com.timothy.themoviedb.home_impl.domain.GetNextPageUseCase
import com.timothy.themoviedb.home_impl.domain.ObserveMovieDetailUseCase
import com.timothy.themoviedb.home_impl.domain.ObserveMoviesUseCase
import com.timothy.themoviedb.home_impl.ui.HomeActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import com.timothy.themoviedb.home_impl.domain.GetMovieDetailUseCase.Params as MovieDetailParams
import com.timothy.themoviedb.home_impl.domain.GetNextPageUseCase.Params as NextPageParams
import com.timothy.themoviedb.home_impl.domain.ObserveMovieDetailUseCase.Params as DetailParams

@InstallIn(SingletonComponent::class)
@Module
object HomeModule {

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideRepository(repository: MovieRepositoryImpl): MovieRepository = repository

    @Singleton
    @Provides
    fun provideAction(
        getNextPageUseCase: GetNextPageUseCase,
        observeMoviesUseCase: ObserveMoviesUseCase,
        getMovieDetailUseCase: GetMovieDetailUseCase,
        observeMovieDetailUseCase: ObserveMovieDetailUseCase
    ) = object : HomeAction {
        override fun getNextPage(page: Int) = getNextPageUseCase(NextPageParams(page))
        override fun observeMovies() = observeMoviesUseCase()
        override fun getMovieDetail(id: Long) = getMovieDetailUseCase(MovieDetailParams(id))
        override fun observeMovieDetail(id: Long) = observeMovieDetailUseCase(DetailParams(id))
    }

    @Singleton
    @Provides
    fun provideHomeDestination() = object : HomeDestination {
        override fun createHomeIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }
}