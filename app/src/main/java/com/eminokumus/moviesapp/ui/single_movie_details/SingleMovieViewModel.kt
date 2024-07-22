package com.eminokumus.moviesapp.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.moviesapp.data.repository.NetworkState
import com.eminokumus.moviesapp.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SingleMovieViewModel @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    movieId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieDetailsRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }
//    val movieDetailsMutableLiveData = MutableLiveData<MovieDetails>()
//    val _movieDetails: LiveData<MovieDetails> get() = movieDetailsMutableLiveData

    val networkState: LiveData<NetworkState> by lazy {
        movieDetailsRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
//    fun getMovie(movieId: Int){
//        movieDetailsMutableLiveData.value = movieDetailsRepository.fetchSingleMovieDetails(compositeDisposable,movieId).value
//    }

}