package com.themoviedb.test.domain.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MovieVideosResponseModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<Video>?
)

@Keep
data class Video(
    @SerializedName("id")
    val id: String?,
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("official")
    val official: Boolean?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("site")
    val site: String?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("type")
    val type: String?
)