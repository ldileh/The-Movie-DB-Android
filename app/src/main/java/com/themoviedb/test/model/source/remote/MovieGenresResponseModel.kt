package com.themoviedb.test.model.source.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieGenresResponseModel(
    @SerializedName("genres")
    val genres: List<Genre>?
)