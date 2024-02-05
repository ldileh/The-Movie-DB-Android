package com.themoviedb.test.ui.view.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.test.databinding.ItemMovieDetailDefaultBinding
import com.themoviedb.test.databinding.ItemMovieDetailFieldBinding
import com.themoviedb.test.databinding.ItemMovieDetailTitleBinding
import com.themoviedb.test.databinding.ItemMovieDetailTitleSubBinding
import com.themoviedb.test.model.ui.MovieDetailModel

class MovieDetailAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mItems = mutableListOf<MovieDetailModel>()

    class DefaultViewHolder(
        private val binding: ItemMovieDetailDefaultBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: MovieDetailModel.Default){
            binding.root.text = item.text
        }
    }

    class TitleViewHolder(
        private val binding: ItemMovieDetailTitleBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: MovieDetailModel.Title){
            binding.root.text = item.text
        }
    }

    class SubTitleViewHolder(
        private val binding: ItemMovieDetailTitleSubBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: MovieDetailModel.SubTitle){
            binding.root.text = item.text
        }
    }

    class FieldViewHolder(
        private val binding: ItemMovieDetailFieldBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: MovieDetailModel.Field){
            binding.apply {
                tvLabel.text = item.label
                tvValue.text = item.value
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType){
            TYPE_TITLE -> TitleViewHolder(
                ItemMovieDetailTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            TYPE_SUB_TITLE -> SubTitleViewHolder(
                ItemMovieDetailTitleSubBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            TYPE_FIELD -> FieldViewHolder(
                ItemMovieDetailFieldBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> DefaultViewHolder(
                ItemMovieDetailDefaultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TitleViewHolder -> holder.bind(mItems[position] as MovieDetailModel.Title)
            is SubTitleViewHolder -> holder.bind(mItems[position] as MovieDetailModel.SubTitle)
            is FieldViewHolder -> holder.bind(mItems[position] as MovieDetailModel.Field)
            is DefaultViewHolder -> holder.bind(mItems[position] as MovieDetailModel.Default)
        }
    }

    override fun getItemViewType(position: Int): Int = when(mItems[position]){
        is MovieDetailModel.Title -> TYPE_TITLE
        is MovieDetailModel.SubTitle -> TYPE_SUB_TITLE
        is MovieDetailModel.Field -> TYPE_FIELD
        else -> TYPE_DEFAULT
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<MovieDetailModel>){
        mItems.apply {
            clear()
            addAll(items)
        }

        notifyDataSetChanged()
    }

    companion object{

        private const val TYPE_TITLE = 1
        private const val TYPE_SUB_TITLE = 2
        private const val TYPE_FIELD = 3
        private const val TYPE_DEFAULT = 4
    }
}