package com.example.password_genreator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.password_genreator.R
import com.example.password_genreator.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

    }

}