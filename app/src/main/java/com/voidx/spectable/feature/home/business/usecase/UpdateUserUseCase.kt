package com.voidx.spectable.feature.home.business.usecase

import com.google.firebase.auth.FirebaseAuth
import com.voidx.spectable.feature.home.business.HomeEffect
import com.voidx.spectable.property.UserIdProperty
import io.reactivex.rxjava3.core.Observable

interface UpdateUserUseCase {

    operator fun invoke(): Observable<HomeEffect>

    class Impl(
            private val auth: FirebaseAuth,
            userIdProperty: UserIdProperty
    ): UpdateUserUseCase {

        private var userID: String? by userIdProperty

        override fun invoke(): Observable<HomeEffect> {
            val userID = auth.currentUser?.uid
            this.userID = userID

            return Observable.just(HomeEffect.Nothing)
        }
    }
}
