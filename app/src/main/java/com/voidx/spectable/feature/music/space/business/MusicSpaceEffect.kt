package com.voidx.spectable.feature.music.space.business

import com.voidx.spectable.feature.music.space.Song
import java.lang.Exception

sealed class MusicSpaceEffect {

    object UserEmptySongList: MusicSpaceEffect()

    object Loading: MusicSpaceEffect()

    data class Error(
            val exception: Exception
    ): MusicSpaceEffect()

    data class UserSongListAdded(
            val song: Song
    ): MusicSpaceEffect()

    data class UserSongListRemoved(
            val song: Song
    ): MusicSpaceEffect()

    data class UserSongListRetrieved(
            val songs: List<Song>
    ): MusicSpaceEffect()
}
