package com.voidx.spectable.feature.music.search.business

import com.voidx.spectable.feature.music.space.Song

sealed class SearchSongEffect {

    object Loading : SearchSongEffect()

    object NoTermSpecified: SearchSongEffect()

    data class Result(
        val songs: List<Song>
    ) : SearchSongEffect()

    object EmptyResult : SearchSongEffect()

    data class Error(
        val exception: Throwable
    ): SearchSongEffect()

    object SongAdded: SearchSongEffect()
}
