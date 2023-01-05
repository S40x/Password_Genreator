package com.example.password_genreator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Data::class], version = 0)
abstract class DadaBaseBuild():RoomDatabase() {

    abstract fun getDao():DataDao

    companion object{

        fun buildCreatedObj(cobtext:Context)=
            Room.databaseBuilder(cobtext.applicationContext,DadaBaseBuild::class.java,"data.db")
                .allowMainThreadQueries().build()


    }
}