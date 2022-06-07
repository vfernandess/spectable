package com.voidx.spectable.feature.music.space.business.usecase

import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect
import com.voidx.spectable.firebase.FirebaseFirestoreProxy
import com.voidx.spectable.property.UserIdProperty
import io.reactivex.rxjava3.core.Observable

interface GetMySongsUseCase {

    operator fun invoke(): Observable<MusicSpaceEffect>

    class Impl(
        private val proxy: FirebaseFirestoreProxy,
        userIdProperty: UserIdProperty
    ) : GetMySongsUseCase {

        private val userID: String? by userIdProperty

        override fun invoke(): Observable<MusicSpaceEffect> {
            return Observable.create { emitter ->
                val userID = userID ?: ""
                proxy.retrieve("music", userID) { snapshots, error ->
                    if (error != null) {
                        emitter.onNext(MusicSpaceEffect.Error(error))
                        return@retrieve
                    }

                    snapshots
                        ?.takeIf { it.isEmpty() }
                        .run {
                            emitter.onNext(MusicSpaceEffect.UserEmptySongList)
                        }
                }
            }
        }
    }
}
