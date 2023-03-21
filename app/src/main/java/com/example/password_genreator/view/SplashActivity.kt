package com.example.password_genreator.view

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.password_genreator.R
import com.example.password_genreator.app.App
import com.example.password_genreator.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView = findViewById<ImageView>(R.id.imageView_logo)

        imageView.animate()
            .apply {
                duration = 1900
                rotation(360f)
                    .start()
            }
        Handler().postDelayed({

            val intent = Intent(this, JoinRulesOrMain::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }


}

