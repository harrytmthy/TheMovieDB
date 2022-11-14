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

package com.timothy.themoviedb.ui.util

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.timothy.themoviedb.ui.R
import com.timothy.themoviedb.ui.ext.hideKeyboard

internal object SnackbarUtil {

    internal fun showErrorSnackBar(
        view: View,
        message: CharSequence,
        @Duration duration: Int = LENGTH_LONG,
        actionText: CharSequence? = null,
        action: View.OnClickListener? = null
    ) {
        createSnackbar(
            view = view,
            message = message,
            duration = duration,
            backgroundTint = ContextCompat.getColor(view.context, R.color.primary_red),
            actionText = actionText,
            action = action
        )?.show()
    }

    internal fun showSuccessSnackbar(
        view: View,
        message: CharSequence,
        @Duration duration: Int = LENGTH_LONG,
        actionText: CharSequence? = null,
        action: View.OnClickListener? = null
    ) {
        createSnackbar(
            view = view,
            message = message,
            duration = duration,
            backgroundTint = ContextCompat.getColor(view.context, R.color.primary_green),
            actionText = actionText,
            action = action
        )?.show()
    }

    private fun createSnackbar(
        view: View,
        message: CharSequence,
        @Duration duration: Int,
        backgroundTint: Int? = null,
        actionText: CharSequence? = null,
        actionTextColor: Int = ContextCompat.getColor(view.context, android.R.color.white),
        action: View.OnClickListener? = null
    ): Snackbar? {
        val snackbar = try {
            Snackbar.make(view, message, duration)
        } catch (e: IllegalArgumentException) {
            return null
        }
        actionText?.let {
            snackbar.setAction(it, action).setActionTextColor(actionTextColor)
        }
        backgroundTint?.let(snackbar::setBackgroundTint)
        view.context?.hideKeyboard(view)
        return snackbar
    }
}