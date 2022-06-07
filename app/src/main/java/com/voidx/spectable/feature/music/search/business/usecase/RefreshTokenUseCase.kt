package com.voidx.spectable.feature.music.search.business.usecase

import com.voidx.spectable.feature.music.search.infra.networking.model.Token
import com.voidx.spectable.feature.music.search.infra.repository.AccessTokenRepository
import com.voidx.spectable.property.SpotifyExpirationTokenDateProperty
import io.reactivex.rxjava3.core.Completable

interface RefreshTokenUseCase {

    fun refreshToken(): Completable

    class Impl(
        private val repository: AccessTokenRepository,
        expirationTokenDateProperty: SpotifyExpirationTokenDateProperty
    ): RefreshTokenUseCase {

        private var expirationDate: Long by expirationTokenDateProperty

        override fun refreshToken(): Completable {
            if(expirationDate > System.currentTimeMillis()) {
                return Completable.complete()
            }

            return repository
                .get()
                .flatMapCompletable(::updateExpirationDate)
        }

        private fun updateExpirationDate(token: Token): Completable {
            return Completable.fromAction {
                val tokenExpiration = if(token.expiration == 0L)
                    3600
                else
                    token.expiration
                expirationDate = System.currentTimeMillis() + (tokenExpiration * 1000L)
            }
        }
    }
}
