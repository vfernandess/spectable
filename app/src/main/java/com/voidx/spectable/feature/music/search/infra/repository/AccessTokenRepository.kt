package com.voidx.spectable.feature.music.search.infra.repository

import android.util.Base64
import com.voidx.spectable.feature.music.search.infra.networking.SpotifyAPI
import com.voidx.spectable.feature.music.search.infra.networking.model.Token
import com.voidx.spectable.property.SpotifyAccessTokenProperty
import com.voidx.spectable.property.SpotifyExpirationTokenDateProperty
import io.reactivex.rxjava3.core.Observable

interface AccessTokenRepository {

    fun get(): Observable<Token>

    fun save(token: Token): Observable<Token>

    class Impl(
        private val remote: AccessTokenRepository,
        private val local: AccessTokenRepository
    ) : AccessTokenRepository {

        override fun get(): Observable<Token> {
            return remote.get().flatMap(::save)
        }

        override fun save(token: Token): Observable<Token> {
            return local.save(token)
        }
    }

    class LocalAccessTokenRepository(
        accessTokenProperty: SpotifyAccessTokenProperty,
        expirationTokenDateProperty: SpotifyExpirationTokenDateProperty
    ) : AccessTokenRepository {

        private var accessToken: String? by accessTokenProperty
        private var expirationDate: Long by expirationTokenDateProperty

        override fun get(): Observable<Token> {
            return accessToken?.let {
                Observable.just(Token(accessToken, expirationDate))
            }
                ?: Observable.empty()
        }

        override fun save(token: Token): Observable<Token> {
            accessToken = token.accessToken
            expirationDate = token.expiration
            return Observable.just(token)
        }
    }

    class RemoteAccessTokenRepository(
        private val spotifyAPI: SpotifyAPI
    ) : AccessTokenRepository {
        override fun get(): Observable<Token> {
            val credentials = "2149d1970a0144dfb4291713befe70d4:7192ff05f1234a788d5b7b11ede7d4c7"
            return spotifyAPI.generateToken(
                auth = "Basic ${Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)}",
                url = "https://accounts.spotify.com/api/token",
                grantType = "client_credentials"
            )
        }

        override fun save(token: Token): Observable<Token> {
            return Observable.empty()
        }
    }
}
