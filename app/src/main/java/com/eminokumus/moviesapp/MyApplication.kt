package com.eminokumus.moviesapp

import android.app.Application
import com.eminokumus.moviesapp.di.AppComponent
import com.eminokumus.moviesapp.di.DaggerAppComponent

class MyApplication: Application() {

        val appComponent: AppComponent by lazy{
            DaggerAppComponent.factory().create()
        }

}