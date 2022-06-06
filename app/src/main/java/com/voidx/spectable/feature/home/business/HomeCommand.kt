package com.voidx.spectable.feature.home.business

sealed class HomeCommand {

    object ShowMyMusics: HomeCommand()

    object ShowMyMovies: HomeCommand()

    object UpdateUserID: HomeCommand()

    object ResetNavigation: HomeCommand()
}
