package com.example.password_genreator.view


import com.example.password_genreator.adapter.MyAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.password_genreator.database.Data

import com.example.password_genreator.databinding.ActivityMainBinding

import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var list= mutableListOf <Data>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = MyAdapter(list,this)




        binding.floatingActionButton2.setOnClickListener {

            val intent = Intent(this, Second::class.java)
            startActivityForResult(intent,2)

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


    }

}
