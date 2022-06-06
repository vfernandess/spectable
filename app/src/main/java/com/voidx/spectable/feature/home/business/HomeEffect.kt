package com.voidx.spectable.feature.home.business

sealed class HomeEffect {
    object ShowMusic: HomeEffect()

    object ShowMovie: HomeEffect()

    object UserNotSignedIn: HomeEffect()

    object UserIsSignedIn: HomeEffect()

    object Nothing: HomeEffect()
}
