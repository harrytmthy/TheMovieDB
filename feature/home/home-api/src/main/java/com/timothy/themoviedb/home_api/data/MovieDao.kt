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

package com.timothy.themoviedb.home_api.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.timothy.themoviedb.home_api.data.entities.MovieEntity
import com.timothy.themoviedb.home_api.data.entities.VideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    fun getMoviesFlow(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    fun getMovieFlow(id: Long): Flow<MovieEntity>

    @Query("SELECT nextPage FROM MovieEntity WHERE id = :id")
    suspend fun getMovieNextPage(id: Long): Int

    @Query("SELECT * FROM VideoEntity WHERE movieId = :movieId")
    fun getVideosFlow(movieId: Long): Flow<VideoEntity>

    @Query(
        """
        SELECT * FROM MovieEntity
        JOIN VideoEntity ON MovieEntity.id = VideoEntity.movieId
        WHERE MovieEntity.id = :movieId
        """
    )
    suspend fun getMovieDetail(movieId: Long): Map<MovieEntity, List<VideoEntity>>

    @Update(onConflict = REPLACE)
    suspend fun updateMovie(entity: MovieEntity)

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAllMovies()

    @Insert(onConflict = REPLACE)
    suspend fun insertMovies(entities: List<MovieEntity>)

    @Transaction
    suspend fun deleteThenInsertMovies(entities: List<MovieEntity>) {
        deleteAllMovies()
        insertMovies(entities)
    }

    @Query("DELETE FROM VideoEntity WHERE movieId = :movieId")
    suspend fun deleteVideos(movieId: Long)

    @Insert(onConflict = REPLACE)
    suspend fun insertVideos(entities: List<VideoEntity>)

    @Transaction
    suspend fun deleteThenInsertVideos(movieId: Long, entities: List<VideoEntity>) {
        deleteVideos(movieId)
        insertVideos(entities)
    }
}