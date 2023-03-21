package com.example.password_genreator.view

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.password_genreator.app.App
import com.example.password_genreator.databinding.ActivityLoginBinding
import ir.tapsell.sdk.*

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private var  adIds:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setFont(this, binding.root)
        super.onCreate(savedInstanceState)
        Tapsell.requestAd(this,
            "63cb7b88e7c8497f1bd70d73",
            TapsellAdRequestOptions(),
            object : TapsellAdRequestListener() {
                override fun onAdAvailable(adId: String) {
                    adIds = adId
                    Log.d("PRerfdfdsfsfs", adId)

                }

                override fun onError(message: String) {
                    Toast.makeText(this@LoginActivity, "$message", Toast.LENGTH_SHORT).show()
                }

            })
        setContentView(binding.root)


        binding.btnRulesAccept.setOnClickListener {
            if (binding.checkboxRules.isChecked) {
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

                App.preferenceEdit.putBoolean("splash", true).apply()
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
            } else {

                Toast.makeText(this, "لطفا قوانین را تایید کنید", Toast.LENGTH_SHORT).show()
            }


        }
    }
        private fun setFont(context: Context, v: View) {
            try {
                if (v is ViewGroup) {
                    val vg = v
                    for (i in 0 until vg.childCount) {
                        val child = vg.getChildAt(i)
                        setFont(context, child)
                    }
                } else if (v is TextView) {
                    v.typeface = Typeface.createFromAsset(context.assets, "font/iransans.ttf")
                }
            } catch (e: Exception) {

            }


        }

}