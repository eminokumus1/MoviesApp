package com.eminokumus.moviesapp.ui.popular_movie

import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
interface PopularMoviesComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): PopularMoviesComponent
    }

    fun inject(activity: MainActivity)

}