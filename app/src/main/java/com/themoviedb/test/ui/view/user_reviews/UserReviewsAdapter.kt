package com.themoviedb.test.ui.view.user_reviews

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.core.utils.ext.dp
import com.themoviedb.core.utils.ext.safe
import com.themoviedb.test.databinding.ItemUserReviewBinding
import com.themoviedb.test.model.source.remote.Review
import com.themoviedb.core.utils.ext.html

class UserReviewsAdapter: PagingDataAdapter<Review, UserReviewsAdapter.ViewHolder>(CharacterComparator) {

    inner class ViewHolder(private val binding: ItemUserReviewBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(position: Int, data: Review){
            binding.apply {
                tvName.text = data.author.safe()
                tvRating.text = "Rating: ${data.authorDetails?.rating.safe()}"
                tvReview.html(data.content.safe())

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                when(position){
                    0 -> params.setMargins(10.dp, 16.dp, 10.dp, 0.dp)
                    else -> params.setMargins(10.dp, 8.dp, 10.dp, 0.dp)
                }

                root.layoutParams = params
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(position, it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    object CharacterComparator : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Review, newItem: Review) =
            oldItem == newItem
    }
}