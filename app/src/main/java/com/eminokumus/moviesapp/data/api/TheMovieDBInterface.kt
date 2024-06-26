package com.eminokumus.moviesapp.data.api

import com.eminokumus.moviesapp.data.vo.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

// https://api.themoviedb.org/3/movie/573435?api_key=666cceee3696eec9c1bc3765ca81e5d4
// https://api.themoviedb.org/3/movie/popular?api_key=666cceee3696eec9c1bc3765ca81e5d4


interface TheMovieDBInterface {
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>



}