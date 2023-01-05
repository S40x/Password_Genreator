package com.example.password_genreator.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dataBase")
data class Data(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "passGenerator")
    var passGenerator: String? = null,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "date")
    var date:String


)
