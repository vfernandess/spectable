package com.voidx.spectable.feature.music.search.view

import androidx.navigation.NavController

interface SearchSongRouter {

    fun back(songAdded: Boolean)

    class Impl(
        private val navigation: NavController
    ): SearchSongRouter {

        override fun back(songAdded: Boolean) {
            navigation.navigateUp()
        }
    }
}
