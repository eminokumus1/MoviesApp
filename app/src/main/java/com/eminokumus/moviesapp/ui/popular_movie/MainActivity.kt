package com.eminokumus.moviesapp.ui.popular_movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eminokumus.moviesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}