package com.eminokumus.moviesapp.di

import com.eminokumus.moviesapp.ui.popular_movie.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(): AppComponent
    }

    fun inject(activity: MainActivity)

}