package com.voidx.spectable.feature.music.space.business

import com.voidx.spectable.feature.music.space.Song

sealed class MusicSpaceCommand {

    object Load: MusicSpaceCommand()

    data class RemoveSong(
            val song: Song
    ): MusicSpaceCommand()
}
