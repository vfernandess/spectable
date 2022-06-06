package com.voidx.spectable.feature.home.view

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.firebase.ui.auth.AuthUI
import com.voidx.spectable.R

interface HomeRouter {

    fun showMusics()

    fun showMovies()

    fun showSignIn(launcher: ActivityResultLauncher<Intent>)

    class Impl(
            private val navigation: NavController
    ): HomeRouter {
        override fun showMusics() {
            navigation.safeNavigate(HomeFragmentDirections.homeToMusicSpace())
        }

        override fun showMovies() {
            TODO("Not yet implemented")
        }

        override fun showSignIn(launcher: ActivityResultLauncher<Intent>) {
            val providers = arrayListOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
            )

            val signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build()
            signInIntent
            launcher.launch(signInIntent)
        }
    }

}

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}
