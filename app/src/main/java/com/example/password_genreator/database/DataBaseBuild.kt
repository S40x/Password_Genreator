package com.example.password_genreator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Data::class], version = 1)
abstract class DataBaseBuild():RoomDatabase() {

    abstract fun getDao():DataDao

    companion object{

        fun buildCreatedObj(cobtext:Context)=
            Room.databaseBuilder(cobtext.applicationContext,DataBaseBuild::class.java,"data.db")
                .allowMainThreadQueries().build()


    }
}