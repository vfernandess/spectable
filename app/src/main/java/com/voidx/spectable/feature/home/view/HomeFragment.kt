package com.voidx.spectable.feature.home.view

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.voidx.spectable.databinding.FragmentHomeBinding
import com.voidx.spectable.feature.home.business.HomeCommand
import com.voidx.spectable.feature.home.business.HomeEffect
import com.voidx.spectable.feature.home.presentation.HomeViewModel
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class HomeFragment : Fragment(), AndroidScopeComponent, ActivityResultCallback<FirebaseAuthUIAuthenticationResult> {

    override val scope: Scope by fragmentScope()

    private val homeViewModel: HomeViewModel by viewModel()
    private val router: HomeRouter by inject { parametersOf(view?.findNavController()) }

    private val signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract(),
            this
    )

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movies.setOnClickListener {
            homeViewModel.invoke(HomeCommand.ShowMyMovies)
        }

        binding.musics.setOnClickListener {
            homeViewModel.invoke(HomeCommand.ShowMyMusics)
        }

        homeViewModel.effect().observe(this.viewLifecycleOwner) { effect ->
            when (effect) {
                HomeEffect.ShowMovie -> {
                    homeViewModel.invoke(HomeCommand.ResetNavigation)
                }

                HomeEffect.ShowMusic -> {
                    homeViewModel.invoke(HomeCommand.ResetNavigation)
                    router.showMusics()
                }

                HomeEffect.UserNotSignedIn ->
                    router.showSignIn(signInLauncher)
            }
        }
    }

    override fun onActivityResult(result: FirebaseAuthUIAuthenticationResult?) {
        if (result?.resultCode == RESULT_OK) {
            homeViewModel.invoke(HomeCommand.UpdateUserID)
        }
    }
}
