package com.themoviedb.test.ui.view.user_reviews

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.core.base.BaseActivityVM
import com.themoviedb.test.databinding.ActivityUserReviewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UserReviewsActivity:
    BaseActivityVM<ActivityUserReviewsBinding, UserReviewViewModel>(ActivityUserReviewsBinding::inflate) {

    private val movieId: Int by lazy { intent.getIntExtra(EXTRA_MOVIE_ID, -1) }

    private val userReviewAdapter: UserReviewsAdapter by lazy { UserReviewsAdapter() }

    override val viewModel: UserReviewViewModel by viewModels()

    override fun ActivityUserReviewsBinding.onViewCreated(savedInstanceState: Bundle?) {
        list.configure()
        viewModel.setMovieIdNumber(movieId)
    }

    override fun observeViewModel(viewModel: UserReviewViewModel) {
        super.observeViewModel(viewModel)

        // init paging response
        lifecycleScope.launchWhenCreated {
            viewModel.responseReviews.collectLatest { pagingData ->
                userReviewAdapter.submitData(pagingData)
            }
        }
    }

    override fun configureToolbar() {
        super.configureToolbar()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun RecyclerView.configure(){
        adapter = userReviewAdapter.apply {
            addLoadStateListener {
                binding.showEmptyItems(this.itemCount < 1)
            }
        }
    }

    private fun ActivityUserReviewsBinding.showEmptyItems(isShow: Boolean = false){
        if (isShow){
            list.visibility = View.GONE
            viewEmpty.visibility = View.VISIBLE
        }else{
            list.visibility = View.VISIBLE
            viewEmpty.visibility = View.GONE
        }
    }

    companion object{

        private const val EXTRA_MOVIE_ID = "movie_id"

        fun showPage(context: Context, movieId: Int){
            context.startActivity(Intent(context, UserReviewsActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            })
        }
    }
}