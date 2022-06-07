package com.voidx.spectable.feature.music.space.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.voidx.spectable.databinding.FragmentMusicSpaceBinding
import com.voidx.spectable.feature.music.space.business.MusicSpaceCommand
import com.voidx.spectable.feature.music.space.business.MusicSpaceCommand.AddNewSong
import com.voidx.spectable.feature.music.space.business.MusicSpaceCommand.ResetNavigation
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect.Error
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect.Loading
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect.UserEmptySongList
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect.UserSongListAdded
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect.UserSongListRemoved
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect.UserSongListRetrieved
import com.voidx.spectable.feature.music.space.presentation.MusicSpaceViewModel
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class MusicSpaceFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private lateinit var binding: FragmentMusicSpaceBinding
    private val musicSpaceViewModel: MusicSpaceViewModel by viewModel()
    private val router: MusicSpaceRouter by inject { parametersOf(view?.findNavController()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicSpaceBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emptyState.setOnClickListener {
            musicSpaceViewModel.invoke(AddNewSong)
        }

        binding.addSongs.setOnClickListener {
            musicSpaceViewModel.invoke(AddNewSong)
        }

        musicSpaceViewModel.effect().observe(viewLifecycleOwner) { effect ->
            when (effect) {
                is Error -> {
                    binding.loading.visibility = GONE
                    binding.groupUserWithSongs.visibility = GONE
                    binding.emptyState.visibility = GONE
                }

                Loading -> {
                    binding.loading.visibility = VISIBLE
                    binding.groupUserWithSongs.visibility = GONE
                    binding.emptyState.visibility = GONE
                }

                UserEmptySongList -> {
                    binding.loading.visibility = GONE
                    binding.groupUserWithSongs.visibility = GONE
                    binding.emptyState.visibility = VISIBLE
                }

                is UserSongListAdded -> TODO()
                is UserSongListRemoved -> TODO()

                is UserSongListRetrieved -> {
                    binding.loading.visibility = GONE
                    binding.groupUserWithSongs.visibility = VISIBLE
                    binding.emptyState.visibility = GONE
                }

                MusicSpaceEffect.AddNewSong -> {
                    musicSpaceViewModel.invoke(ResetNavigation)
                    router.showSearch()
                }
            }
        }

        musicSpaceViewModel.invoke(MusicSpaceCommand.Load)
    }
}
