package com.eminokumus.moviesapp.ui.popular_movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.eminokumus.moviesapp.data.api.TheMovieDBClient
import com.eminokumus.moviesapp.data.api.TheMovieDBInterface
import com.eminokumus.moviesapp.data.repository.NetworkState
import com.eminokumus.moviesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    lateinit var movieRepository: MoviePagingDataRepository

    private val coroutineJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + coroutineJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MoviePagingDataRepository(apiService)

        viewModel = getViewModel()



        val movieAdapter = PopularMoviePagingDataAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (movieAdapter.getItemViewType(position)) {
                    movieAdapter.MOVIE_VIEW_TYPE -> 1     //MOVIE_VIEW_TYPE will occupy 1 out of 3 span
                    else -> 3                             //NETWORK_VIEW_TYPE will occupy 3 out of 3 span
                }
            }
        }

        binding.rvMovieList.layoutManager = gridLayoutManager
        binding.rvMovieList.setHasFixedSize(true)
        binding.rvMovieList.adapter = movieAdapter

        observeProperties(movieAdapter)

        binding.swipeRefreshLayout.setOnRefreshListener {
            movieAdapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }



    }

    private fun observeProperties(movieAdapter: PopularMoviePagingDataAdapter) {
        viewModel.moviePagingData.observe(this) {
            uiScope.launch {
                movieAdapter.submitData(it)
            }
        }

        viewModel.networkState.observe(this) {
            binding.progressBarPopular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            binding.txtErrorPopular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineJob.cancel()
    }


    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}