package com.themoviedb.test.ui.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.core.utils.ext.safe
import com.themoviedb.test.databinding.ItemMovieBinding
import com.themoviedb.test.model.source.remote.Movie
import com.themoviedb.test.util.ext.getImageUrl
import com.themoviedb.test.util.ext.loadImage

class MovieAdapter(
    private val onClickItem: (movieId: Int) -> Unit
): PagingDataAdapter<Movie, MovieAdapter.ViewHolder>(CharacterComparator) {

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data: Movie){
            binding.apply {
                imgBanner.loadImage(getImageUrl(data.posterPath))
                tvName.text = data.title

                root.setOnClickListener {
                    onClickItem(data.id.safe())
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    object CharacterComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }
}