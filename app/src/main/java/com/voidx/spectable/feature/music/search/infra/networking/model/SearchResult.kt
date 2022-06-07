package com.voidx.spectable.feature.music.search.infra.networking.model

import com.google.gson.annotations.SerializedName

data class SearchResult(

    @SerializedName("items")
    val items: List<Track>
)

data class SearchInfo(

    @SerializedName("tracks")
    val tracks: SearchResult
)
