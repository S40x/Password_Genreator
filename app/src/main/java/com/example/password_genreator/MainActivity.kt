package com.example.password_genreator

import android.content.ClipData
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.ClipboardManager
import android.view.Menu
import android.view.MenuInflater
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.lang.StringBuilder
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button_show.setOnClickListener {

            if(editText.text.toString().isEmpty()){
                toastShow("لطفا عددی را برای ورودی بدهید")
                return@setOnClickListener
            }
            if(editText.text.toString().toInt()>25) {
                toastShow("باید عددی که وارد کردید زیر 25باشد")
                return@setOnClickListener
            }
            val n = editText.text.toString().toInt()
            text_view.text = getRandPassword((n))


        }
        text_view.setOnClickListener {
            (it as TextView).text = UUID.randomUUID().toString()
        }
       text_view.image_copy.setOnClickListener{

        
        }

    }

    fun getRandPassword(inputvalidation: Int): String {
        val characterSet = "0123456789abcdefghijklmnopqrstuvwxyz ABCEFGHIJKLMNOPQRSTUVWXYZ"
        val random = Random(System.nanoTime())
        val password = StringBuilder()
        for (i in 0 until inputvalidation) {
            val rIndex = random.nextInt(characterSet.length)
            password.append(characterSet[rIndex])
        }

        return password.toString()
    }
    /*
    toast _show = > function related=(mortabet) to Toast method
     */
    fun toastShow(text:String){
        Toast.makeText(this, "$text", Toast.LENGTH_SHORT).show()
    }
    
}
