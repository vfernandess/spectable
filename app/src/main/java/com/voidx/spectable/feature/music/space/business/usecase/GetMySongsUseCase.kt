package com.voidx.spectable.feature.music.space.business.usecase

import com.google.firebase.firestore.ktx.toObject
import com.voidx.spectable.feature.music.space.Song
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect
import com.voidx.spectable.firebase.FirebaseFirestoreProxy
import com.voidx.spectable.preferences.StringStorageProperty
import io.reactivex.rxjava3.core.Single

interface GetMySongsUseCase {

    fun getSongs(): Single<MusicSpaceEffect>
}

class GetMySongUseCaseImpl(
        private val proxy: FirebaseFirestoreProxy,
        userIdProperty: StringStorageProperty
) : GetMySongsUseCase {

    private val userID: String? by userIdProperty

    override fun getSongs(): Single<MusicSpaceEffect> {
        return Single.create { emitter ->
            val userID = userID ?: ""
            proxy.retrieve("music", userID) { snapshot, error ->
                if (error != null) {
                    emitter.onSuccess(MusicSpaceEffect.Error(error))
                    return@retrieve
                }

                val result = snapshot?.toObject<Song>()
            }
        }
    }
}
