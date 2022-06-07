package com.voidx.spectable.feature.music.search.infra.repository

import com.voidx.spectable.feature.music.search.infra.networking.SpotifyAPI
import com.voidx.spectable.feature.music.search.infra.networking.model.SearchInfo
import com.voidx.spectable.property.SpotifyAccessTokenProperty
import io.reactivex.rxjava3.core.Observable

interface SearchSongRepository {

    fun search(term: String): Observable<SearchInfo>

    class Impl(
        private val spotifyAPI: SpotifyAPI,
        accessTokenProperty: SpotifyAccessTokenProperty
    ) : SearchSongRepository {

        private val accessToken: String? by accessTokenProperty

        override fun search(term: String): Observable<SearchInfo> {
            val auth = "Bearer ${accessToken ?: ""}"
            return spotifyAPI.search(auth, query = "track:$term")
        }
    }
}
