package com.eminokumus.moviesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eminokumus.moviesapp.databinding.ActivityMainBinding
import com.eminokumus.moviesapp.movie_details.SingleMovie

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeActivityBtn.setOnClickListener {
            val intent = Intent(this,SingleMovie::class.java)
            intent.putExtra("movieId", 573435)
            this.startActivity(intent)
        }

    }
}