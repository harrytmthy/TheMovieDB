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

package com.timothy.themoviedb.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.timothy.themoviedb.network.constants.ApiConstants.CONTENT_TYPE
import com.timothy.themoviedb.network.constants.ApiConstants.TMDB_BASE_URL
import com.timothy.themoviedb.network.handler.ApiResponseCallAdapterFactory
import com.timothy.themoviedb.network.interceptors.AccessTokenInterceptor
import com.timothy.themoviedb.network.qualifiers.TmdbApiKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val TIMEOUT_DURATION = 1L

    init {
        System.loadLibrary("native-lib")
    }

    @TmdbApiKey
    @Singleton
    @Provides
    fun provideAccessToken() = getTmdbApiKey()

    @Singleton
    @Provides
    fun provideJsonConverterFactory(): Converter.Factory {
        val json = Json { ignoreUnknownKeys = true }
        return json.asConverterFactory(CONTENT_TYPE.toMediaType())
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        } else {
            level = HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient()
        .newBuilder()
        .addInterceptor(accessTokenInterceptor)
        .addInterceptor(loggingInterceptor)
        .callTimeout(TIMEOUT_DURATION, TimeUnit.MINUTES)
        .connectTimeout(TIMEOUT_DURATION, TimeUnit.MINUTES)
        .readTimeout(TIMEOUT_DURATION, TimeUnit.MINUTES)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        networkStateObserver: NetworkStateObserver,
        jsonConverterFactory: Converter.Factory,
        httpClient: OkHttpClient
    ) = Retrofit.Builder()
        .addCallAdapterFactory(ApiResponseCallAdapterFactory(networkStateObserver))
        .addConverterFactory(jsonConverterFactory)
        .baseUrl(TMDB_BASE_URL)
        .client(httpClient)
        .build()

    private external fun getTmdbApiKey(): String
}