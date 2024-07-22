package com.eminokumus.moviesapp.ui.single_movie_details

import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
interface SingleMovieComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance movieId: Int): SingleMovieComponent
    }

    fun inject(activity: SingleMovieActivity)

}