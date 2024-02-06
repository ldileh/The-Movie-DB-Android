package com.themoviedb.test.util.ext

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

fun generateCircularProgress(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
}