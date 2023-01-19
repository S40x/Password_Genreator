package com.example.password_genreator.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "dataBase")
data class Data(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "passGenerator")
    var passGenerator: String? = "",

    @ColumnInfo(name = "description")
    var description: String?=null,


    @ColumnInfo(name = "daste")

var datse:String=""


)
