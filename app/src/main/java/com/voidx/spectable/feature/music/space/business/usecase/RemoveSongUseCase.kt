package com.voidx.spectable.feature.music.space.business.usecase

import com.voidx.spectable.feature.music.space.Song
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect
import com.voidx.spectable.firebase.FirebaseFirestoreProxy
import com.voidx.spectable.property.UserIdProperty
import io.reactivex.rxjava3.core.Observable

interface RemoveSongUseCase {

    operator fun invoke(song: Song): Observable<MusicSpaceEffect>

    class Impl(
        private val proxy: FirebaseFirestoreProxy,
        userIdProperty: UserIdProperty
    ) : RemoveSongUseCase {

        private val userID: String? by userIdProperty

        override fun invoke(song: Song): Observable<MusicSpaceEffect> {
            return Observable.create { emitter ->
                proxy.delete("music", userID.orEmpty(), song.id.orEmpty()) { success, error ->
                    if (error != null) {
                        emitter.onNext(MusicSpaceEffect.Error(error))
                        return@delete
                    }

                    emitter.onNext(MusicSpaceEffect.Nothing)
                }
            }
        }
    }
}
