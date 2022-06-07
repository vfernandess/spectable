package com.voidx.spectable.feature.music.space.business.usecase

import com.google.firebase.firestore.ktx.toObject
import com.voidx.spectable.feature.music.space.Song
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect
import com.voidx.spectable.firebase.FirebaseFirestoreProxy
import com.voidx.spectable.property.UserIdProperty
import io.reactivex.rxjava3.core.Observable

interface MusicSpaceChangedUseCase {

    operator fun invoke(): Observable<MusicSpaceEffect>

    class Impl(
        private val proxy: FirebaseFirestoreProxy,
        userIdProperty: UserIdProperty
    ) : MusicSpaceChangedUseCase {

        private val userID: String? by userIdProperty

        override fun invoke(): Observable<MusicSpaceEffect> {
            return Observable.create { emitter ->

                proxy
                    .listenForInsertion(
                        name = "music",
                        document = userID.orEmpty(),
                        onEmpty = {
                            emitter.onNext(MusicSpaceEffect.UserEmptySongList)
                        },
                        onInserted = { snapshot, error ->
                            if (error != null) {
                                emitter.onNext(MusicSpaceEffect.Error(error))
                                return@listenForInsertion
                            }

                            snapshot?.toObject<Song>()?.let {
                                emitter.onNext(MusicSpaceEffect.UserSongListAdded(it))
                            }
                        }
                    )
            }
        }
    }
}
