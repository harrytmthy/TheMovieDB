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

package com.timothy.themoviedb.splash_impl

import android.content.Context
import android.content.Intent
import com.timothy.themoviedb.core.usecases.invoke
import com.timothy.themoviedb.splash_api.data.ConfigDataSource
import com.timothy.themoviedb.splash_api.domain.ConfigRepository
import com.timothy.themoviedb.splash_api.ui.SplashAction
import com.timothy.themoviedb.splash_api.ui.SplashNavigation
import com.timothy.themoviedb.splash_impl.data.ConfigLocalDataSource
import com.timothy.themoviedb.splash_impl.data.ConfigRepositoryImpl
import com.timothy.themoviedb.splash_impl.data.ConfigService
import com.timothy.themoviedb.splash_impl.domain.GetConfigUseCase
import com.timothy.themoviedb.splash_impl.ui.SplashActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SplashModule {

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit) = retrofit.create(ConfigService::class.java)

    @Singleton
    @Provides
    fun provideDataSource(localDataSource: ConfigLocalDataSource) = object : ConfigDataSource {
        override suspend fun getConfigFromLocal() = localDataSource.getConfig()
    }

    @Singleton
    @Provides
    fun provideRepository(repository: ConfigRepositoryImpl): ConfigRepository = repository

    @Singleton
    @Provides
    fun provideAction(getConfigUseCase: GetConfigUseCase) = object : SplashAction {
        override fun getConfig() = getConfigUseCase()
    }

    @Singleton
    @Provides
    fun provideSplashNavigation() = object : SplashNavigation {
        override fun createSplashIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }
}