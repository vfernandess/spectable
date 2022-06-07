package com.voidx.spectable.feature.music.search.business

import com.voidx.spectable.feature.music.space.Song

sealed class SearchSongCommand {

    object Load: SearchSongCommand()

    data class AddSong(
        val song: Song
    ): SearchSongCommand()

    data class Search(
        val term: String
    ): SearchSongCommand()
}
