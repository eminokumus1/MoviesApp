package com.eminokumus.moviesapp.di

import com.eminokumus.moviesapp.ui.popular_movie.MainActivity
import com.eminokumus.moviesapp.ui.popular_movie.PopularMoviesComponent
import com.eminokumus.moviesapp.ui.single_movie_details.SingleMovie
import com.eminokumus.moviesapp.ui.single_movie_details.SingleMovieComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppSubComponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(): AppComponent
    }

    fun popularMoviesComponent(): PopularMoviesComponent.Factory

    fun singleMovieComponent(): SingleMovieComponent.Factory

}