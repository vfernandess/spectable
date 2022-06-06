package com.voidx.spectable.feature.home.business.mapper

import com.voidx.spectable.feature.home.business.HomeCommand
import com.voidx.spectable.feature.home.business.HomeEffect

interface UserCheckMapper {

    fun map(command: HomeCommand, effect: HomeEffect): HomeEffect

    class Impl : UserCheckMapper {

        override fun map(command: HomeCommand, effect: HomeEffect): HomeEffect {
            return when (effect) {
                is HomeEffect.UserIsSignedIn -> {
                    if (command == HomeCommand.ShowMyMovies)
                        return HomeEffect.ShowMovie

                    return HomeEffect.ShowMusic
                }
                else -> effect
            }
        }
    }
}
