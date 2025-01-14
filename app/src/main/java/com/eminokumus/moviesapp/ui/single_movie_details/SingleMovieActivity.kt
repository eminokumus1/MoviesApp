package com.eminokumus.moviesapp.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.eminokumus.moviesapp.MyApplication
import com.eminokumus.moviesapp.data.api.POSTER_BASE_URL
import com.eminokumus.moviesapp.data.repository.NetworkState
import com.eminokumus.moviesapp.data.vo.MovieDetails
import com.eminokumus.moviesapp.databinding.ActivitySingleMovieBinding
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class SingleMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleMovieBinding

    @Inject
    lateinit var viewModel: SingleMovieViewModel

    @Inject
    lateinit var movieDetailsRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        val movieId = intent.getIntExtra("movieId", 1)

        (application as MyApplication).appComponent.singleMovieComponent().create(movieId)
            .inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivitySingleMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.movieDetails.observe(this) {
            bindUI(it)
        }

        viewModel.networkState.observe(this) {
            binding.progressBarPopular.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE

            binding.txtError.visibility =
                if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        }
//        viewModel.getMovie(movieId)

    }

    private fun bindUI(it: MovieDetails) {
        binding.run {
            movieTitle.text = it.title
            movieTagline.text = it.tagline
            movieReleaseDate.text = it.releaseDate
            movieRating.text = it.rating.toString()
            movieRuntime.text = it.runtime.toString() + "minutes"
            movieOverview.text = it.overview
        }

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        binding.run {
            movieBudget.text = formatCurrency.format(it.budget)
            movieRevenue.text = formatCurrency.format(it.revenue)
        }

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(binding.ivMoviePoster)

    }

//    private fun getViewModel(movieId: Int): SingleMovieViewModel {
//        return ViewModelProvider(this, object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return SingleMovieViewModel(movieDetailsRepository, movieId) as T
//            }
//        })[SingleMovieViewModel::class.java]
//    }
}