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

package com.timothy.themoviedb.home_impl.data

import com.timothy.themoviedb.core.paging.PagedData
import com.timothy.themoviedb.home_api.domain.MovieRepository
import com.timothy.themoviedb.home_api.domain.model.Movie.Companion.from
import com.timothy.themoviedb.home_api.domain.model.MovieDetail
import com.timothy.themoviedb.splash_api.data.ConfigDataSource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val configDataSource: ConfigDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieNetworkDataSource: MovieNetworkDataSource
) : MovieRepository {

    override fun getNextPage(page: Int) = flow {
        val response = movieNetworkDataSource.getPopularMovies(page)
        movieLocalDataSource.saveMovies(
            entities = response.results.map { it.toEntity(response.getNextPage()) },
            page = response.page
        )
        emit(Unit)
    }

    override fun loadMovies() = movieLocalDataSource.getMoviesFlow().map { movies ->
        var nextPage = 1
        val data = movies.map {
            nextPage = nextPage.coerceAtLeast(it.nextPage)
            from(
                entity = it,
                backdropUrl = configDataSource.getConfigFromLocal().getBackdropUrl(it.backdropPath),
                posterUrl = configDataSource.getConfigFromLocal().getPosterUrl(it.posterPath)
            )
        }
        PagedData(data, nextPage)
    }

    override fun getMovieDetail(movieId: Long) = flow {
        val response = movieNetworkDataSource.getMovieDetail(movieId)
        val nextPage = movieLocalDataSource.getMovieNextPage(movieId)
        movieLocalDataSource.updateMovieDetail(
            movie = response.toMovieEntity(nextPage),
            videos = response.toVideoEntities()
        )
        emit(Unit)
    }

    override fun loadMovieDetail(movieId: Long) = movieLocalDataSource.getMovieDetail(movieId).map {
        val entityPair = it.entries.firstOrNull()?.toPair() ?: return@map MovieDetail.EMPTY
        val movie = entityPair.first
        MovieDetail.from(
            entityPair = entityPair,
            backdropUrl = configDataSource.getConfigFromLocal().getBackdropUrl(movie.backdropPath),
            posterUrl = configDataSource.getConfigFromLocal().getPosterUrl(movie.posterPath)
        )
    }
}