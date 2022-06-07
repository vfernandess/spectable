package com.voidx.spectable.feature.music.search.business.usecase

import com.voidx.spectable.feature.music.search.business.SearchSongEffect
import com.voidx.spectable.feature.music.search.business.mapper.SongMapper
import com.voidx.spectable.feature.music.search.infra.repository.SearchSongRepository
import io.reactivex.rxjava3.core.Observable


interface SearchSongUseCase {

    operator fun invoke(term: String?): Observable<SearchSongEffect>

    class Impl(
        private val repository: SearchSongRepository,
        private val mapper: SongMapper
    ): SearchSongUseCase {

        override fun invoke(term: String?): Observable<SearchSongEffect> {
            if(term.isNullOrBlank()) {
                return Observable.just(SearchSongEffect.NoTermSpecified)
            }

            return repository
                .search(term)
                .map<SearchSongEffect> {
                    val songs = it.tracks.items.map(mapper::map)

                    SearchSongEffect.Result(songs)
                }
                .startWith(Observable.just(SearchSongEffect.Loading))
        }
    }
}
