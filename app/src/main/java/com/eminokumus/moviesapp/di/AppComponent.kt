package com.eminokumus.moviesapp.di

import com.eminokumus.moviesapp.ui.popular_movie.MainActivity
import com.eminokumus.moviesapp.ui.single_movie_details.SingleMovie
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: SingleMovie)

}