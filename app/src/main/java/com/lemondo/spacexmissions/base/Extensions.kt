package com.lemondo.spacexmissions.base

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.lemondo.spacexmissions.R
import com.lemondo.spacexmissions.utils.Resource

/**
 * Created by Manuchar Zakariadze on 2/28/22
 */

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {

    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An unknown error occured")
    }
}

fun Fragment.snackbar(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.goneIf(isGone: Boolean) = if (isGone) gone() else show()

fun View.hideIf(isHide: Boolean) = if (isHide) hide() else show()





















