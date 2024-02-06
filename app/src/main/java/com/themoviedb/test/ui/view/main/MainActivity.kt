package com.themoviedb.test.ui.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.themoviedb.core.base.BaseActivityVM
import com.themoviedb.core.utils.PageMessageUtil
import com.themoviedb.core.utils.ext.hide
import com.themoviedb.core.utils.ext.safe
import com.themoviedb.core.utils.ext.show
import com.themoviedb.core.widget.ContainerView
import com.themoviedb.test.R
import com.themoviedb.test.databinding.ActivityMainBinding
import com.themoviedb.test.ui.view.adapter.GenreAdapter
import com.themoviedb.test.ui.view.detail.DetailMovieActivity
import com.themoviedb.test.util.ext.observeFlows
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter


@AndroidEntryPoint
class MainActivity : BaseActivityVM<ActivityMainBinding, MainViewModel>(ActivityMainBinding::inflate) {

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter{ movieId ->
            DetailMovieActivity.showPage(this@MainActivity, movieId)
        }
    }

    private val genreAdapter: GenreAdapter by lazy { GenreAdapter() }

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

        observeFlows {
            viewModel.genreFilter.collectLatest { selected ->
                genreAdapter.setItems(viewModel.itemsGenre.filter { it.isSelected })

                binding.viewGenre.apply { if(!selected.isNullOrEmpty()) show() else hide() }
            }
        }

        // init paging response
        lifecycleScope.launchWhenCreated {
            viewModel.responseMovie.collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }

            movieAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.listMovie.scrollToPosition(0) }
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
                showDialogGenre()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun ActivityMainBinding.initListeners(){
        viewRefresh.setOnRefreshListener {
            movieAdapter.refresh()
        }

        getContainerView().addListenerOnRetry{
            movieAdapter.refresh()
        }

        viewGenre.setOnClickListener {
            showDialogGenre()
        }
        listGenre.setOnClickListener {
            showDialogGenre()
        }
    }

    private fun showDialogGenre(){
        GenreDialog().show(supportFragmentManager, GenreDialog::class.simpleName)
    }

    private fun configureList(){
        binding.apply {
            listMovie.apply {
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }

                movieAdapter.addLoadStateListener {
                    binding.viewRefresh.isRefreshing = it.refresh is LoadState.Loading

                    getContainerView().apply {
                        val viewType = when(val state = it.refresh){
                            is LoadState.Loading -> ContainerView.SHOW_VIEW_LOADING
                            is LoadState.Error -> {
                                getContainerView().setErrorMessage(state.error.message.safe())
                                ContainerView.SHOW_VIEW_ERROR
                            }
                            else -> ContainerView.SHOW_VIEW_CONTENT
                        }
                        setView(viewType)
                    }
                }

                adapter = movieAdapter
            }

            listGenre.adapter = genreAdapter
        }
    }

    private fun getContainerView(): ContainerView = binding.containerView
}