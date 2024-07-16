package com.eminokumus.moviesapp.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.eminokumus.moviesapp.data.api.TheMovieDBInterface
import com.eminokumus.moviesapp.data.repository.MovieDetailsNetworkDataSource
import com.eminokumus.moviesapp.data.repository.NetworkState
import com.eminokumus.moviesapp.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val apiService: TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieDetailsResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
}