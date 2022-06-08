package com.voidx.spectable.feature.home.business.usecase

import com.voidx.spectable.feature.home.business.HomeEffect
import com.voidx.spectable.property.UserIdProperty
import io.reactivex.rxjava3.core.Observable

interface CheckUserSignedInUseCase {

    operator fun invoke(): Observable<HomeEffect>

    class Impl(
            userIdProperty: UserIdProperty
    ) : CheckUserSignedInUseCase {

        private var userID: String? by userIdProperty

        override fun invoke(): Observable<HomeEffect> {
            val state = if (userID == null)
                HomeEffect.UserNotSignedIn
            else
                HomeEffect.UserIsSignedIn

            return Observable.just(state)
        }
    }
}
