package com.example.password_genreator.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.password_genreator.R
import com.example.password_genreator.app.App
import com.example.password_genreator.databinding.ActivityLoginBinding
import ir.tapsell.sdk.*

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private var  adIds:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityLoginBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        Tapsell.requestAd(this,
            "63cb7b88e7c8497f1bd70d73",
            TapsellAdRequestOptions(),
            object : TapsellAdRequestListener() {
                override fun onAdAvailable(adId: String) {
                    adIds = adId
                    Log.d("PRerfdfdsfsfs",adId)

                }
                override fun onError(message: String) {
                    Toast.makeText(this@LoginActivity, "$message", Toast.LENGTH_SHORT).show()
                }

            })
        setContentView(binding.root)


        binding.btnRulesAccept.setOnClickListener {
            if (binding.checkboxRules.isChecked){
                Tapsell.showAd(this,
                    "63cb7b88e7c8497f1bd70d73",
                    adIds,
                    TapsellShowOptions(),
                    object : TapsellAdShowListener() {
                        override fun onOpened() {

                        }
                        override fun onClosed() {}
                        override fun onError(message: String) {}
                        override fun onRewarded(completed: Boolean) {}
                    })

                App.preferenceEdit.putBoolean("splash",true).apply()
                val intent = Intent(this,MainActivity::class.java)

                startActivity(intent)
                finish()
            }else{

                Toast.makeText(this, "لطفا قوانین را تایید کنید", Toast.LENGTH_SHORT).show()
            }


        }

    }
}