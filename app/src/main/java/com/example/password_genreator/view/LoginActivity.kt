package com.example.password_genreator.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.password_genreator.R
import com.example.password_genreator.app.App
import com.example.password_genreator.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRulesAccept.setOnClickListener {
            if (binding.checkboxRules.isChecked){

                App.preferenceEdit.putBoolean("splash",true).apply()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else{

                Toast.makeText(this, "لطفا قوانین را تایید کنید", Toast.LENGTH_SHORT).show()
            }


        }
    }
}