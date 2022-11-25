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

package com.timothy.themoviedb.home_api.domain.model

import com.timothy.themoviedb.home_api.data.entities.MovieEntity
import com.timothy.themoviedb.home_api.data.entities.VideoEntity

data class MovieDetail(val movie: Movie, val videos: List<Video>) {

    companion object {

        val EMPTY = MovieDetail(movie = Movie.EMPTY, videos = emptyList())

        fun from(
            entityPair: Pair<MovieEntity, List<VideoEntity>>,
            backdropUrl: String,
            posterUrl: String
        ) = MovieDetail(
            movie = Movie.from(entityPair.first, backdropUrl, posterUrl),
            videos = entityPair.second.map(Video::from)
        )
    }
}