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

package com.timothy.themoviedb.ui.base

import android.os.Handler
import com.airbnb.epoxy.TypedEpoxyController
import com.timothy.themoviedb.ui.BuildConfig
import timber.log.Timber

abstract class BaseEpoxyController<T : Any> : TypedEpoxyController<T> {

    constructor() : super()

    constructor(
        modelBuildingHandler: Handler,
        diffingHandler: Handler
    ) : super(modelBuildingHandler, diffingHandler)

    init {
        this.setFilterDuplicates(true)
    }

    override fun onExceptionSwallowed(exception: RuntimeException) {
        super.onExceptionSwallowed(exception)
        Timber.e(exception)
        if (BuildConfig.DEBUG) {
            throw exception
        }
    }
}