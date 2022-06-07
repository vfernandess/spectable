package com.voidx.spectable.feature.music.search.infra.networking

import com.voidx.spectable.feature.music.search.infra.networking.model.SearchInfo
import com.voidx.spectable.feature.music.search.infra.networking.model.Token
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface SpotifyAPI {

    @FormUrlEncoded
    @POST
    fun generateToken(
        @Header("Authorization") auth: String,
        @Url url:String,
        @Field("grant_type") grantType: String
    ): Observable<Token>

    @GET("search?type=track")
    fun search(
        @Header("Authorization") auth: String,
        @Query("q") query: String
    ): Observable<SearchInfo>
}
