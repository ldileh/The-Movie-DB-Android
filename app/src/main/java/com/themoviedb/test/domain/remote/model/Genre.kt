package com.themoviedb.test.domain.remote.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Genre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)