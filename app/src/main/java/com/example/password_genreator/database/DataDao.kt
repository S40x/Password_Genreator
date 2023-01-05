package com.example.password_genreator.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DataDao {


    @Query("SELECT * FROM `database`")
    fun addAll():MutableList<Data>

    @Insert
    fun insert(data: Data)

    @Update
    fun update(data: Data)

    @Delete
    fun delete(data: Data)


}