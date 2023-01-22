package com.example.password_genreator.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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

        supportActionBar?.hide()

        if (!App.preferences.getBoolean("splash",false)){
            startActivity(Intent(this,LoginActivity::class.java))
        }else{
            startActivity(Intent(this,MainActivity::class.java))
        }
        finish()
    }

}