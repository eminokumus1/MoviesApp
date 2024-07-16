package com.eminokumus.moviesapp.di

import com.eminokumus.moviesapp.ui.popular_movie.PopularMoviesComponent
import com.eminokumus.moviesapp.ui.single_movie_details.SingleMovieComponent
import dagger.Module

@Module(subcomponents = [PopularMoviesComponent::class, SingleMovieComponent::class])
class AppSubComponents {
}