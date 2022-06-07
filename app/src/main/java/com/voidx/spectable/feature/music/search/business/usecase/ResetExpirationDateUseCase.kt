package com.voidx.spectable.feature.music.search.business.usecase

import com.voidx.spectable.property.SpotifyExpirationTokenDateProperty
import retrofit2.HttpException
import java.net.HttpURLConnection

interface ResetExpirationDateUseCase {

    operator fun invoke(throwable: Throwable): Boolean

    class Impl(
        expirationTokenDateProperty: SpotifyExpirationTokenDateProperty
    ) : ResetExpirationDateUseCase {

        private var expirationDate: Long by expirationTokenDateProperty

        override fun invoke(throwable: Throwable): Boolean {
            if (throwable is HttpException) {
                if (throwable.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    expirationDate = 0L
                    return true
                }
            }

            return false
        }
    }
}
