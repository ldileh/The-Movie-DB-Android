package com.themoviedb.test.model.ui

import androidx.annotation.Keep
import com.themoviedb.test.model.source.remote.Genre

@Keep
data class GenreModel(
    val data: Genre,
    var isSelected: Boolean = false
)

fun List<Genre>.translate(): List<GenreModel> = map { GenreModel(it) }