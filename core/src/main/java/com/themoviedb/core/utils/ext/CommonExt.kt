package com.themoviedb.core.utils.ext

import android.content.res.Resources

fun Int?.safe() = this ?: 0

fun String?.safe() = this ?: ""

fun Double?.safe() = this ?: 0.0

fun Long?.safe() = this ?: 0L

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()