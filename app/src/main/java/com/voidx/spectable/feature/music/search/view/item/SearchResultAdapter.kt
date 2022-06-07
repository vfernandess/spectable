package com.voidx.spectable.feature.music.search.view.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.voidx.spectable.databinding.SearchResultItemBinding
import com.voidx.spectable.feature.music.space.Song

class SearchResultAdapter(
    private val onItemSelected: ((song: Song) -> Unit)?
) : RecyclerView.Adapter<SearchResultViewHolder>() {

    private var songs: List<Song> = listOf()

    fun notifyResults(songs: List<Song>) {
        this.songs = songs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding =
            SearchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        songs
            .elementAtOrNull(position)
            ?.apply {
                holder.putValues(this)

                holder.binding.root.setOnClickListener {
                    onItemSelected?.invoke(this)
                }
            }
    }

    override fun getItemCount(): Int = songs.size
}

class SearchResultViewHolder(
    val binding: SearchResultItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun putValues(song: Song) {
        binding.musicTitle.text = song.name
        binding.musicArtist.text = song.artist

        Picasso.get().load(song.thumbnail).into(binding.cover)
    }
}
