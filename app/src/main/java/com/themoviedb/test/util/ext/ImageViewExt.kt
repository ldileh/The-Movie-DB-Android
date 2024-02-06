package com.themoviedb.test.util.ext

import android.widget.ImageView
import com.themoviedb.test.util.GlideApp

fun ImageView.loadImage(url: String?){
    GlideApp.with(context)
        .load(url)
        .placeholder(generateCircularProgress(context))
        .into(this)
}