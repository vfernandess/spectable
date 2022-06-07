package com.voidx.spectable.feature.music.space.view.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.voidx.spectable.databinding.MusicSpaceItemBinding
import com.voidx.spectable.feature.music.space.Song

class MusicSpaceAdapter(
    private val onItemSelected: (song: Song) -> Unit,
    private val onItemRemoved: (song: Song) -> Unit,
    private val onShowItemCovers: (first: Song?, second: Song?, third: Song?, fourth: Song?) -> Unit
) : RecyclerView.Adapter<MusicSpaceItem>() {

    private var songs: MutableSet<Song> = mutableSetOf()

    private fun showCovers(position: Int) {
        if ((0..3).contains(position).not()) {
            return
        }

        onShowItemCovers.invoke(
            songs.elementAtOrNull(0),
            songs.elementAtOrNull(1),
            songs.elementAtOrNull(2),
            songs.elementAtOrNull(3)
        )
    }

    fun notifySongAdded(song: Song) {
        val size = itemCount
        if (songs.add(song)) {
            showCovers(size)
            notifyItemInserted(size)
        }
    }

    fun notifySongRemoved(position: Int) {
        showCovers(position)
        songs
            .elementAtOrNull(position)
            ?.takeIf { songs.remove(it) }
            ?.run {
                notifyItemRemoved(position)
                onItemRemoved.invoke(this)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicSpaceItem {
        val binding =
            MusicSpaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicSpaceItem(binding)
    }

    override fun onBindViewHolder(holder: MusicSpaceItem, position: Int) {
        songs.elementAtOrNull(position)?.let { song ->
            holder.putValues(song)

            holder.binding.root.setOnClickListener {
                onItemSelected.invoke(song)
            }
        }
    }

    override fun getItemCount(): Int = songs.size
}

class MusicSpaceItem(
    val binding: MusicSpaceItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun putValues(song: Song) {
        binding.musicTitle.text = song.name
        binding.musicArtist.text = song.artist

        Picasso.get().load(song.thumbnail).into(binding.cover)
    }
}
