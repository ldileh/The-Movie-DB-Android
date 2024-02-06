package com.themoviedb.test.ui.view.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.themoviedb.core.base.BaseActivityVM
import com.themoviedb.core.widget.ContainerView
import com.themoviedb.test.R
import com.themoviedb.test.databinding.ActivityDetailMovieBinding
import com.themoviedb.test.model.ui.state.MovieDetailState
import com.themoviedb.test.ui.view.user_reviews.UserReviewsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMovieActivity:
    BaseActivityVM<ActivityDetailMovieBinding, DetailMovieViewModel>(
        ActivityDetailMovieBinding::inflate
    ) {

    private val movieId: Int by lazy { intent.getIntExtra(EXTRA_MOVIE_ID, -1) }

    private val detailAdapter: MovieDetailAdapter by lazy { MovieDetailAdapter() }
    private val videoTrailerAdapter: MovieTrailerAdapter by lazy {
        MovieTrailerAdapter{ video ->
            openBrowser("https://www.youtube.com/watch?v=${video.key}")
        }
    }

    override fun toolbarId(): Int = R.id.toolbar

    override val viewModel: DetailMovieViewModel by viewModels()

    override fun ActivityDetailMovieBinding.onViewCreated(savedInstanceState: Bundle?) {
        configureList()

        viewModel.getDetailMovie(movieId)

        btnReviews.setOnClickListener {
            UserReviewsActivity.showPage(this@DetailMovieActivity, movieId)
        }

        getContainerView().addListenerOnRetry{
            viewModel.getDetailMovie(movieId)
        }
    }

    override fun observeViewModel(viewModel: DetailMovieViewModel) {
        super.observeViewModel(viewModel.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED){
                    stateUI.collectLatest {
                        when(it){
                            is MovieDetailState.Success -> {
                                binding.viewToolbar.appBar.setExpanded(true)

                                getContainerView().setView(ContainerView.SHOW_VIEW_CONTENT)

                                Glide.with(this@DetailMovieActivity)
                                    .load(it.bannerUrl)
                                    .into(binding.viewToolbar.imgBanner)

                                detailAdapter.setItems(it.detailData)
                                videoTrailerAdapter.setItems(it.videosTrailer)
                            }
                            is MovieDetailState.Failed -> {
                                binding.viewToolbar.appBar.setExpanded(false)

                                getContainerView().apply {
                                    setView(ContainerView.SHOW_VIEW_ERROR)
                                    setErrorMessage(it.message)
                                }
                            }
                            is MovieDetailState.Idle -> {
                                getContainerView().setView(ContainerView.SHOW_VIEW_LOADING)
                            }
                        }
                    }
                }
            }
        })
    }

    override fun configureToolbar() {
        super.configureToolbar()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var isShow = true
        var scrollRange = -1
        binding.viewToolbar.appBar.addOnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                binding.viewToolbar.collapsingToolbar.title = getString(R.string.page_detail_movie)
                isShow = true
            } else if (isShow) {
                binding.viewToolbar.collapsingToolbar.title = " "
                isShow = false
            }
        }
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

    private fun configureList(){
        binding.apply {
            listDetail.adapter = detailAdapter
            listVideos.adapter = videoTrailerAdapter
        }
    }

    private fun openBrowser(url: String){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun getContainerView(): ContainerView = binding.viewContainer

    companion object{

        private const val EXTRA_MOVIE_ID = "movie_id"

        fun showPage(context: Context, movieId: Int){
            context.startActivity(Intent(context, DetailMovieActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            })
        }
    }
}