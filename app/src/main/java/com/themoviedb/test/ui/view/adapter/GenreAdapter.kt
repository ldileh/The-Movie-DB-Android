package com.themoviedb.test.ui.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.test.databinding.ItemGenreCapsuleBinding
import com.themoviedb.test.model.source.remote.Genre
import com.themoviedb.test.model.ui.GenreModel

class GenreAdapter: RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    private val mItems = mutableListOf<GenreModel>()

    class ViewHolder(
        private val binding: ItemGenreCapsuleBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(data: GenreModel){
            binding.root.text = data.data.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGenreCapsuleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<GenreModel>){
        mItems.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear(){
        mItems.clear()
        notifyDataSetChanged()
    }
}