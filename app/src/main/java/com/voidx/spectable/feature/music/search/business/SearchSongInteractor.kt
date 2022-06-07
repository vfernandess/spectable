package com.voidx.spectable.feature.music.search.business

import com.voidx.spectable.arch.Interactor
import com.voidx.spectable.feature.music.search.business.usecase.AddSongUseCase
import com.voidx.spectable.feature.music.search.business.usecase.RefreshTokenUseCase
import com.voidx.spectable.feature.music.search.business.usecase.ResetExpirationDateUseCase
import com.voidx.spectable.feature.music.search.business.usecase.SearchSongUseCase
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class SearchSongInteractor(
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val searchSongUseCase: SearchSongUseCase,
    private val addSongUseCase: AddSongUseCase,
    private val resetExpirationDateUseCase: ResetExpirationDateUseCase
) : Interactor<SearchSongCommand, SearchSongEffect> {

    override fun invoke(command: SearchSongCommand): Observable<SearchSongEffect> {
        return when(command) {
            is SearchSongCommand.AddSong ->
                addSongUseCase(command.song)

            SearchSongCommand.Load ->
                Observable.just(SearchSongEffect.NoTermSpecified)

            is SearchSongCommand.Search ->
                refreshTokenUseCase
                    .refreshToken()
                    .andThen(searchSongUseCase(command.term))
                    .debounce(1, TimeUnit.SECONDS)
                    .retry(1) {
                        resetExpirationDateUseCase(it)
                    }
        }
    }
}
