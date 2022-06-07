package com.voidx.spectable.feature.music.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.voidx.spectable.databinding.FragmentSearchSongBinding
import com.voidx.spectable.feature.music.search.business.SearchSongCommand
import com.voidx.spectable.feature.music.search.business.SearchSongEffect
import com.voidx.spectable.feature.music.search.presentation.SearchSongViewModel
import com.voidx.spectable.feature.music.search.view.item.SearchResultAdapter
import com.voidx.spectable.feature.music.space.Song
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class SearchSongFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private val searchSongViewModel: SearchSongViewModel by viewModel()
    private val router: SearchSongRouter by inject { parametersOf(view?.findNavController()) }

    private lateinit var binding: FragmentSearchSongBinding
    private val adapter by lazy {
        SearchResultAdapter(::onSongSelected)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchSongBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.results.adapter = adapter

        binding.back.setOnClickListener {
            router.back(false)
        }

        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchSongViewModel.invoke(SearchSongCommand.Search(binding.search.text.toString()))
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        binding.search.doAfterTextChanged {
            searchSongViewModel.invoke(SearchSongCommand.Search(binding.search.text.toString()))
        }

        searchSongViewModel.effect().observe(viewLifecycleOwner) { effect ->
            when (effect) {
                SearchSongEffect.EmptyResult -> {
                    binding.results.visibility = GONE
                    binding.loading.visibility = GONE
                    binding.emptyState.visibility = VISIBLE
                    binding.emptyStateMessage.text = "Nenhuma música encontrada"
                }

                is SearchSongEffect.Error -> {
                    binding.results.visibility = GONE
                    binding.loading.visibility = GONE
                    binding.emptyState.visibility = GONE
                }

                SearchSongEffect.Loading -> {
                    binding.results.visibility = VISIBLE
                    binding.loading.visibility = VISIBLE
                    binding.emptyState.visibility = GONE
                }

                SearchSongEffect.NoTermSpecified -> {
                    binding.results.visibility = GONE
                    binding.loading.visibility = GONE
                    binding.emptyState.visibility = VISIBLE
                    binding.emptyStateMessage.text = "Busque pelas músicas que você ama"
                }

                is SearchSongEffect.Result -> {
                    binding.results.visibility = VISIBLE
                    binding.loading.visibility = GONE
                    binding.emptyState.visibility = GONE
                    adapter.notifyResults(effect.songs)
                }

                SearchSongEffect.SongAdded -> {
                    router.back(true)
                }
            }
        }

        searchSongViewModel.invoke(SearchSongCommand.Load)
    }

    private fun onSongSelected(song: Song) {
        searchSongViewModel.invoke(SearchSongCommand.AddSong(song))
    }
}
