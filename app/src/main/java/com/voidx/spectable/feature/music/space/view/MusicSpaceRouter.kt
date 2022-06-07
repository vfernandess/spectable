package com.voidx.spectable.feature.music.space.view

import androidx.navigation.NavController

interface MusicSpaceRouter {

    fun showSearch()

    class Impl(
        private val navigation: NavController
    ): MusicSpaceRouter {

        override fun showSearch() {
            val direction = MusicSpaceFragmentDirections.musicSpaceToSearchSong()
            navigation.navigate(direction)
        }
    }
}
