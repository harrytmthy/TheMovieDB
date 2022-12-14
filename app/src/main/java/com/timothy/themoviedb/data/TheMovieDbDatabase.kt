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

package com.timothy.themoviedb.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.timothy.themoviedb.core.converters.CollectionTypeConverters
import com.timothy.themoviedb.data.DatabaseConstants.DB_VERSION
import com.timothy.themoviedb.home_api.data.MovieDao
import com.timothy.themoviedb.home_api.data.entities.MovieEntity
import com.timothy.themoviedb.home_api.data.entities.VideoEntity
import com.timothy.themoviedb.splash_api.data.ConfigDao
import com.timothy.themoviedb.splash_api.data.ConfigEntity

@Database(
    entities = [ConfigEntity::class, MovieEntity::class, VideoEntity::class],
    version = DB_VERSION
)
@TypeConverters(value = [CollectionTypeConverters::class])
abstract class TheMovieDbDatabase : RoomDatabase() {

    abstract fun configurationDao(): ConfigDao

    abstract fun movieDao(): MovieDao
}