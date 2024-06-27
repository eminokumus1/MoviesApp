package com.eminokumus.moviesapp.movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.eminokumus.moviesapp.R
import com.eminokumus.moviesapp.data.api.POSTER_BASE_URL
import com.eminokumus.moviesapp.data.api.TheMovieDBClient
import com.eminokumus.moviesapp.data.api.TheMovieDBInterface
import com.eminokumus.moviesapp.data.repository.NetworkState
import com.eminokumus.moviesapp.data.vo.MovieDetails
import com.eminokumus.moviesapp.databinding.ActivitySingleMovieBinding
import java.text.NumberFormat
import java.util.Locale

class SingleMovie : AppCompatActivity() {
    private lateinit var binding: ActivitySingleMovieBinding
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = 1

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this) {
            bindUI(it)
        }

        viewModel.networkState.observe(this) {
            binding.progressBarPopular.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE

            binding.txtError.visibility =
                if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        }


    }

    private fun bindUI(it: MovieDetails) {
        binding.movieTitle.text = it.title
        binding.movieTagline.text = it.tagline
        binding.movieReleaseDate.text = it.releaseDate
        binding.movieRating.text = it.rating.toString()
        binding.movieRuntime.text = it.runtime.toString() + "minutes"
        binding.movieOverview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        binding.movieBudget.text = formatCurrency.format(it.budget)
        binding.movieRevenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(binding.ivMoviePoster)

    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SingleMovieViewModel(movieDetailsRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}