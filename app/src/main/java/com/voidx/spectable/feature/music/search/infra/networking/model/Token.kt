package com.voidx.spectable.feature.music.search.infra.networking.model

import com.google.gson.annotations.SerializedName

data class Token(

    @SerializedName("access_token")
    val accessToken: String?,

    @SerializedName("expiration")
    val expiration: Long,
)
