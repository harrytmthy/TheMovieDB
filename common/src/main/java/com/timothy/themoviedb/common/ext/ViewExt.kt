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

package com.timothy.themoviedb.ui.ext

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

inline fun RecyclerView.addLoadMoreListener(
    prefetchOffset: Int = 2,
    crossinline shouldLoadMore: () -> Boolean,
    crossinline onLoadMore: () -> Unit
) = addOnScrollListener(
    object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!isScrolledToLast(prefetchOffset) || !shouldLoadMore()) return
            onLoadMore()
        }
    }
)

fun RecyclerView.isScrolledToLast(prefetchOffset: Int): Boolean {
    val lm = (layoutManager as? LinearLayoutManager) ?: return false
    val lastVisibleItemPosition = lm.findLastVisibleItemPosition()
    return lm.itemCount > 1 && lastVisibleItemPosition >= lm.itemCount - prefetchOffset - 1
}