package com.example.password_genreator.view


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.example.password_genreator.adapter.MyAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.password_genreator.database.Data
import com.example.password_genreator.database.DataBaseBuild
import com.example.password_genreator.database.DataDao

import com.example.password_genreator.databinding.ActivityMainBinding


import kotlin.random.Random


class MainActivity : AppCompatActivity(), MyAdapter.GetBuildItemSetView {
    lateinit var binding: ActivityMainBinding
    private var list = mutableListOf<Data>()
    lateinit var dataDao: DataDao
    val requestCodes = 1
    lateinit var adapter: MyAdapter
    var password: Int? = null
    private lateinit var textViewShowPassword: TextView
    lateinit var datas: Data
    lateinit var clipboardManager: ClipboardManager
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dataDao = DataBaseBuild.buildCreatedObj(this).getDao()


        getAll()
        adapter = MyAdapter(list, this, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        textViewShowPassword = binding.textviewShow


        binding.floatingActionButton2.setOnClickListener {
            val intent = Intent(this, Second::class.java)
            startActivityForResult(intent, requestCodes)
        }
        binding.buttonSave.setOnClickListener {

            if (textViewShowPassword.text.equals("")) {
                Toast.makeText(this, "طفا ابتدا رمزی بسازید", Toast.LENGTH_SHORT).show()

            } else {


                select(datas)
                adapter.notifyDataSetChanged()
                getAll()
                textViewShowPassword.setOnClickListener {


                }
            }
            textViewShowPassword.setOnClickListener {
                copyText(textViewShowPassword.rootView)
            }
        }


    }

    fun copyText(view: View) {
        val text = binding.textviewShow.text.toString()
        if (text.isNotEmpty()) {
            clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("key", text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(applicationContext, "Copied", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "No text to be copied", Toast.LENGTH_SHORT).show()
        }
    }

    fun getRandPassword(inputvalidation: Int): String {
        val characterSet = "0123456789abcdefghijklmnopqrstuvwxyzABCEFGHIJKLMNOPQRSTUVWXYZ"
        val random = Random(System.nanoTime())
        val password = StringBuilder()
        for (i in 0 until inputvalidation) {
            val rIndex = random.nextInt(characterSet.length)
            password.append(characterSet[rIndex])
        }

        return password.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 || resultCode == 2) {
            password = data?.getStringExtra(ConstObject.lenghtPass).toString().toInt()
            val show = getRandPassword(password.toString().toInt())
            textViewShowPassword.text = show
            datas = Data(
                id = null,
                description = data?.getStringExtra(ConstObject.description_password),
                passGenerator = show
            )
        }

    }

    fun getAll() {
        list.clear()
        val r = dataDao.addAll()
        list.addAll(r)
    }

    fun select(data: Data) {
        dataDao.insert(data)
    }



    override fun textPassViewCopy(position: Int) {

        var list = list[position].passGenerator
        if (list != null) {

            clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("key", list)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(applicationContext, "Copied", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "No text to be copied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun deleteItem() {
        TODO("Not yet implemented")
    }

}



