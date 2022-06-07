package com.voidx.spectable.feature.music.search.business.mapper

import com.voidx.spectable.feature.music.search.infra.networking.model.Track
import com.voidx.spectable.feature.music.space.Song

interface SongMapper {

    fun map(track: Track): Song

    class Impl: SongMapper {

        override fun map(track: Track): Song {
            return Song(
                id = track.id,
                name = track.name,
                artist = track.artists?.firstOrNull()?.name,
                thumbnail = track.album?.images?.firstOrNull { it.height == 300 }?.url,
                url = track.uri
            )
        }
    }
}
