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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    lateinit var movieRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MoviePagedListRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = PopularMoviePagesListAdapter(this)

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

        viewModel.moviePagedList.observe(this) {
            movieAdapter.submitList(it)
        }

        viewModel.networkState.observe(this) {
            binding.progressBarPopular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            binding.txtErrorPopular.visibility =
                if(viewModel.listIsEmpty() && it == NetworkState.ERROR){
                    View.VISIBLE
                }else{
                    View.GONE
                }

            if (!viewModel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }
        }


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