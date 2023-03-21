package com.example.password_genreator.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.password_genreator.R
import com.example.password_genreator.app.App

class JoinRulesOrMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_rules_or_main)


        supportActionBar?.hide()

        if (!App.preferences.getBoolean("splash",false)){
            startActivity(Intent(this,LoginActivity::class.java))
        }else{
            startActivity(Intent(this,MainActivity::class.java))
        }

        finish()

    }

    }
