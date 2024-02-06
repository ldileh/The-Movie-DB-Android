package com.themoviedb.test.ui.view.user_reviews

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.core.base.BaseActivityVM
import com.themoviedb.core.utils.ext.safe
import com.themoviedb.core.widget.ContainerView
import com.themoviedb.test.databinding.ActivityUserReviewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UserReviewsActivity: BaseActivityVM<ActivityUserReviewsBinding, UserReviewViewModel>(
        ActivityUserReviewsBinding::inflate
) {

    private val movieId: Int by lazy { intent.getIntExtra(EXTRA_MOVIE_ID, -1) }

    private val userReviewAdapter: UserReviewsAdapter by lazy { UserReviewsAdapter() }

    override val viewModel: UserReviewViewModel by viewModels()

    override fun ActivityUserReviewsBinding.onViewCreated(savedInstanceState: Bundle?) {
        list.configure()

        viewModel.setMovieIdNumber(movieId)

        getContainerView().addListenerOnRetry{
            userReviewAdapter.refresh()
        }
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
                val isEmpty = this.itemCount < 1

                getContainerView().apply {
                    val viewType = when(val state = it.refresh){
                        is LoadState.Loading -> ContainerView.SHOW_VIEW_LOADING
                        is LoadState.Error -> {
                            setErrorMessage(state.error.message.safe())
                            ContainerView.SHOW_VIEW_ERROR
                        }
                        else -> {
                            if(isEmpty){
                                ContainerView.SHOW_VIEW_EMPTY
                            }else{
                                ContainerView.SHOW_VIEW_CONTENT
                            }
                        }
                    }
                    setView(viewType)
                }
            }
        }
    }

    private fun getContainerView(): ContainerView = binding.root

    companion object{

        private const val EXTRA_MOVIE_ID = "movie_id"

        fun showPage(context: Context, movieId: Int){
            context.startActivity(Intent(context, UserReviewsActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            })
        }
    }
}