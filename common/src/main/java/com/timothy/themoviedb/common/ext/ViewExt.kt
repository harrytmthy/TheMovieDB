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

package com.timothy.themoviedb.common.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timothy.themoviedb.ui.GlideApp

fun ImageView.loadUrl(url: String, @DrawableRes defaultResId: Int? = null) {
    val glide = GlideApp.with(context).load(url)
    defaultResId?.let { glide.error(it) }
    glide.into(this)
}

inline fun RecyclerView.addLoadMoreListener(
    crossinline canLoadMore: () -> Boolean,
    crossinline onLoadMore: () -> Unit,
    prefetchOffset: Int = 5
) = addOnScrollListener(
    object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!isScrolledToLast(prefetchOffset) || !canLoadMore()) return
            onLoadMore()
        }
    }
)

fun RecyclerView.isScrolledToLast(prefetchOffset: Int): Boolean {
    val lm = (layoutManager as? LinearLayoutManager) ?: return false
    val lastVisibleItemPosition = lm.findLastVisibleItemPosition()
    return lm.itemCount > 1 && lastVisibleItemPosition >= lm.itemCount - prefetchOffset - 1
}