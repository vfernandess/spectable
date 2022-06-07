package com.voidx.spectable.feature.music.space.business

import com.voidx.spectable.arch.Interactor
import com.voidx.spectable.feature.music.space.business.usecase.MusicSpaceChangedUseCase
import com.voidx.spectable.feature.music.space.business.usecase.RemoveSongUseCase
import io.reactivex.rxjava3.core.Observable

class MusicSpaceInteractor(
    private val musicSpaceChangedUseCase: MusicSpaceChangedUseCase,
    private val removeSongUseCase: RemoveSongUseCase
) : Interactor<MusicSpaceCommand, MusicSpaceEffect> {

    override fun invoke(command: MusicSpaceCommand): Observable<MusicSpaceEffect> {
        return when (command) {
            MusicSpaceCommand.Load ->
                musicSpaceChangedUseCase()
//                getMySongsUseCase()
//                    .startWith(Observable.just(MusicSpaceEffect.Loading))

            is MusicSpaceCommand.RemoveSong ->
                removeSongUseCase(command.song)

            MusicSpaceCommand.AddNewSong ->
                Observable.just(MusicSpaceEffect.AddNewSong)

            MusicSpaceCommand.Reset ->
                Observable.just(MusicSpaceEffect.Nothing)
        }
    }
}
