package com.themoviedb.test.ui.view.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.themoviedb.core.base.BaseActivityVM
import com.themoviedb.test.R
import com.themoviedb.test.databinding.ActivityDetailMovieBinding
import com.themoviedb.test.ui.view.user_reviews.UserReviewsActivity
import com.themoviedb.test.ui.viewmodel.DetailMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        viewModel.apply {
            getDetailMovie(movieId)
            getVideosTrailer(movieId)
        }

        btnReviews.setOnClickListener {
            UserReviewsActivity.showPage(this@DetailMovieActivity, movieId)
        }
    }

    override fun observeViewModel(viewModel: DetailMovieViewModel) {
        super.observeViewModel(viewModel.apply {

            bannerImage.observe(this@DetailMovieActivity){ url ->
                Glide.with(this@DetailMovieActivity)
                    .load(url)
                    .into(binding.viewToolbar.imgBanner)
            }

            detailMovie.observe(this@DetailMovieActivity){ items ->
                detailAdapter.setItems(items)
            }

            videosTrailer.observe(this@DetailMovieActivity){ items ->
                videoTrailerAdapter.setItems(items)
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

    companion object{

        private const val EXTRA_MOVIE_ID = "movie_id"

        fun showPage(context: Context, movieId: Int){
            context.startActivity(Intent(context, DetailMovieActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            })
        }
    }
}