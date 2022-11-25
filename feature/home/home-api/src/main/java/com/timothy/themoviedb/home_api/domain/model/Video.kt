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

import com.timothy.themoviedb.home_api.data.constants.MovieDataConstants.VIMEO_URL
import com.timothy.themoviedb.home_api.data.constants.MovieDataConstants.YOUTUBE_URL
import com.timothy.themoviedb.home_api.data.entities.VideoEntity
import com.timothy.themoviedb.home_api.domain.enums.VideoType
import com.timothy.themoviedb.home_api.domain.enums.VideoType.Companion.from
import com.timothy.themoviedb.home_api.domain.enums.VideoType.VIMEO
import com.timothy.themoviedb.home_api.domain.enums.VideoType.YOUTUBE

data class Video(val name: String, val url: String, val type: VideoType) {

    companion object {

        fun from(entity: VideoEntity): Video {
            val type = from(entity.site)
            return Video(
                name = entity.name,
                url = getVideoUrl(type, entity.key),
                type = type
            )
        }

        private fun getVideoUrl(type: VideoType, key: String) = buildString {
            append(
                when (type) {
                    YOUTUBE -> YOUTUBE_URL
                    VIMEO -> VIMEO_URL
                    else -> throw IllegalStateException("Unknown Video Type")
                }
            )
            append(key)
        }
    }
}