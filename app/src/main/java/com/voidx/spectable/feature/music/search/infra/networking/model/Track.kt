package com.voidx.spectable.feature.music.search.infra.networking.model

import com.google.gson.annotations.SerializedName

data class Track(

    @SerializedName("name")
    val name: String?,

    @SerializedName("artists")
    val artists: List<Artist>?,

    @SerializedName("album")
    val album: Album?,

    @SerializedName("uri")
    val uri: String?
)

data class Artist(

    @SerializedName("name")
    val name: String?
)

data class Album(

    @SerializedName("images")
    val images: List<Image>
)

data class Image(
    @SerializedName("height")
    val height: Int,

    @SerializedName("url")
    val url: String?
)

