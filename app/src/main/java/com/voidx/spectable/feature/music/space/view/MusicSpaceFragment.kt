package com.voidx.spectable.feature.music.space.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.voidx.spectable.databinding.FragmentMusicSpaceBinding
import com.voidx.spectable.feature.music.space.business.MusicSpaceCommand
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect
import com.voidx.spectable.feature.music.space.presentation.MusicSpaceViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class MusicSpaceFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private lateinit var binding: FragmentMusicSpaceBinding
    private val musicSpaceViewModel: MusicSpaceViewModel by viewModel()

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
            TODO("call router to add songs")
        }

        musicSpaceViewModel.effect().observe(viewLifecycleOwner) { effect ->
            when(effect) {
                is MusicSpaceEffect.Error -> {
                    binding.loading.visibility = GONE
                    binding.groupUserWithSongs.visibility = GONE
                    binding.emptyState.visibility = GONE
                }

                MusicSpaceEffect.Loading -> {
                    binding.loading.visibility = VISIBLE
                    binding.groupUserWithSongs.visibility = GONE
                    binding.emptyState.visibility = GONE
                }

                MusicSpaceEffect.UserEmptySongList -> {
                    binding.loading.visibility = GONE
                    binding.groupUserWithSongs.visibility = GONE
                    binding.emptyState.visibility = VISIBLE
                }

                is MusicSpaceEffect.UserSongListAdded -> TODO()
                is MusicSpaceEffect.UserSongListRemoved -> TODO()

                is MusicSpaceEffect.UserSongListRetrieved -> {
                    binding.loading.visibility = GONE
                    binding.groupUserWithSongs.visibility = VISIBLE
                    binding.emptyState.visibility = GONE
                }
            }
        }

        musicSpaceViewModel.invoke(MusicSpaceCommand.Load)
    }
}
