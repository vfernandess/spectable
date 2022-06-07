package com.voidx.spectable.feature.music.search.business.usecase

import com.voidx.spectable.feature.music.search.business.SearchSongEffect
import com.voidx.spectable.feature.music.space.Song
import com.voidx.spectable.firebase.FirebaseFirestoreProxy
import com.voidx.spectable.property.UserIdProperty
import io.reactivex.rxjava3.core.Observable

interface AddSongUseCase {

    operator fun invoke(song: Song): Observable<SearchSongEffect>

    class Impl(
        private val proxy: FirebaseFirestoreProxy,
        userIdProperty: UserIdProperty
    ) : AddSongUseCase {

        private val userID by userIdProperty

        override fun invoke(song: Song): Observable<SearchSongEffect> {
            return Observable.create { emitter ->
                proxy.save(
                    name = "music",
                    document = userID ?: "",
                    value = song
                ) { success, error ->
                    if (success) {
                        emitter.onNext(SearchSongEffect.SongAdded)
                    } else {
                        error?.let {
                            emitter.onNext(SearchSongEffect.Error(it))
                        }
                    }
                }
            }
        }
    }
}
