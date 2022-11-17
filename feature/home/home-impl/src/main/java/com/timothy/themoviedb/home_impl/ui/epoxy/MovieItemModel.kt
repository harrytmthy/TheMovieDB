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

package com.timothy.themoviedb.home_impl.ui.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.timothy.themoviedb.core.utils.LocalDateTimeUtil.getLocalDateText
import com.timothy.themoviedb.home_api.domain.Movie
import com.timothy.themoviedb.home_impl.R
import com.timothy.themoviedb.home_impl.databinding.ItemMovieListBinding
import com.timothy.themoviedb.ui.ext.loadUrl

@EpoxyModelClass
abstract class MovieItemModel : EpoxyModelWithHolder<MovieItemModel.Holder>() {

    @EpoxyAttribute
    lateinit var data: Movie

    @EpoxyAttribute
    lateinit var onItemClick: (movieId: Long) -> Unit

    override fun bind(holder: Holder) {
        with(holder) {
            binding.clContent.setOnClickListener { onItemClick(data.id) }
            binding.ivPoster.loadUrl(data.posterUrl)
            binding.tvTitle.text = data.title
            binding.tvDate.text = getLocalDateText(data.releaseDate)
            binding.tvDescription.text = data.overview
        }
    }

    override fun getDefaultLayout() = R.layout.item_movie_list

    class Holder : EpoxyHolder() {

        lateinit var binding: ItemMovieListBinding
            private set

        override fun bindView(itemView: View) {
            binding = ItemMovieListBinding.bind(itemView)
        }
    }
}