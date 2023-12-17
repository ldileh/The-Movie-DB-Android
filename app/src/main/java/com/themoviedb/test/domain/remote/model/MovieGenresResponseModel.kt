package com.themoviedb.test.domain.remote.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieGenresResponseModel(
    @SerializedName("genres")
    val genres: List<Genre>?
)