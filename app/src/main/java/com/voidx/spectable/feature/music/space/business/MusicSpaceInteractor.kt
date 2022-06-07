package com.voidx.spectable.feature.music.space.business

import com.voidx.spectable.arch.Interactor
import com.voidx.spectable.feature.music.space.business.usecase.GetMySongsUseCase
import com.voidx.spectable.feature.music.space.business.usecase.MusicSpaceChangedUseCase
import io.reactivex.rxjava3.core.Observable

class MusicSpaceInteractor(
    private val getMySongsUseCase: GetMySongsUseCase,
    private val musicSpaceChangedUseCase: MusicSpaceChangedUseCase
): Interactor<MusicSpaceCommand, MusicSpaceEffect> {

    override fun invoke(command: MusicSpaceCommand): Observable<MusicSpaceEffect> {
        return when(command) {
            MusicSpaceCommand.Load ->
                musicSpaceChangedUseCase()
//                getMySongsUseCase()
//                    .startWith(Observable.just(MusicSpaceEffect.Loading))

            is MusicSpaceCommand.RemoveSong -> TODO()

            MusicSpaceCommand.AddNewSong ->
                Observable.just(MusicSpaceEffect.AddNewSong)

            MusicSpaceCommand.Reset ->
                Observable.just(MusicSpaceEffect.Nothing)
        }
    }
}
