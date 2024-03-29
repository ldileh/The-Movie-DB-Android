package com.themoviedb.test.ui.view.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.test.databinding.ItemMovieTrailerBinding
import com.themoviedb.test.model.source.remote.Video
import com.themoviedb.test.util.ext.loadImage

class MovieTrailerAdapter(
    private val onClick: (Video) -> Unit
): RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder>() {

    private val mItems = mutableListOf<Video>()

    inner class ViewHolder(
        private val binding: ItemMovieTrailerBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Video){
            binding.apply {
                imgBanner.loadImage("https://img.youtube.com/vi/${item.key}/hqdefault.jpg")
                tvDescription.text = item.name

                root.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieTrailerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Video>){
        mItems.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }
}