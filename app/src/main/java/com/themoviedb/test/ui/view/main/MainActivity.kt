package com.themoviedb.test.ui.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.themoviedb.core.base.BaseActivityVM
import com.themoviedb.core.utils.PageMessageUtil
import com.themoviedb.test.R
import com.themoviedb.test.databinding.ActivityMainBinding
import com.themoviedb.test.ui.view.detail.DetailMovieActivity
import com.themoviedb.test.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter


@AndroidEntryPoint
class MainActivity : BaseActivityVM<ActivityMainBinding, MainViewModel>(ActivityMainBinding::inflate) {

    private val movieAdapter: MovieAdapter = MovieAdapter{ movieId ->
        DetailMovieActivity.showPage(this@MainActivity, movieId)
    }

    override val viewModel: MainViewModel by viewModels()

    override fun toolbarId(): Int = R.id.toolbar

    override var messageType: PageMessageUtil.Type = PageMessageUtil.Type.SNACK_BAR

    override fun ActivityMainBinding.onViewCreated(savedInstanceState: Bundle?) {
        configureList()
        initListeners()
    }

    override fun observeViewModel(viewModel: MainViewModel) {
        super.observeViewModel(viewModel.apply {
            triggerRefreshMovie.observe(this@MainActivity){ isRefresh ->
                if(isRefresh){
                    movieAdapter.refresh()
                }
            }
        })

        // init paging response
        lifecycleScope.launchWhenCreated {
            viewModel.responseMovie.collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }

            movieAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.list.scrollToPosition(0) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_genre -> {
                GenreDialog()
                    .show(supportFragmentManager, GenreDialog::class.simpleName)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun ActivityMainBinding.initListeners(){
        viewRefresh.setOnRefreshListener {
            movieAdapter.refresh()
        }
    }

    private fun configureList(){
        binding.list.apply {
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

            movieAdapter.addLoadStateListener {
                binding.viewRefresh.isRefreshing = it.refresh is LoadState.Loading
            }
            adapter = movieAdapter
        }
    }
}