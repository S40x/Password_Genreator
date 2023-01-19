package com.example.password_genreator.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import ir.tapsell.sdk.Tapsell

class App():Application() {

    companion object{
        lateinit var preferences:SharedPreferences
        lateinit var preferenceEdit:SharedPreferences.Editor
    }

    lateinit var context: Context
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        preferences = getSharedPreferences("did", MODE_PRIVATE)
        preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context)
        preferenceEdit = preferences.edit()
        preferenceEdit.apply()
        Tapsell.initialize(this,"nnoqftnholtkopljdlbahqintjiahrjetplcncpoafcheabalnjbabctdimqkakdhspdkp")
    }

}