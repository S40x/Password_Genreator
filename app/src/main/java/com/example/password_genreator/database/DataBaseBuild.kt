package com.example.password_genreator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Data::class], version = 5)

abstract class DataBaseBuild :RoomDatabase() {

    abstract fun getDao():DataDao


    companion object {

        fun buildCreatedObj(cobtext: Context) =
            Room.databaseBuilder(cobtext.applicationContext, DataBaseBuild::class.java, "data.db")
                .fallbackToDestructiveMigration()
//                .addMigrations(MIGRATION_1_3)
                .allowMainThreadQueries().build()




        }
    }
//val MIGRATION_1_3 = object : Migration(3, 4) {
//    override fun migrate(database: SupportSQLiteDatabase) {
////        // Remove first and last name column from profile
////        // create new table
////        database.execSQL("CREATE TABLE IF NOT EXISTS `profile_new` (`id` INTEGER NOT NULL, `nickName` TEXT NOT NULL, `bio` TEXT NOT NULL, PRIMARY KEY(`id`))")
////        // create nickname if needed
////        database.execSQL("UPDATE `dataBase` SET `nickName` = `firstName` || ' ' || `lastName` WHERE `nickName` IS NULL")
////        // copy data to new table
////        database.execSQL("INSERT INTO `profile_new` (`id`, `nickName`, `bio`) SELECT `id`, `nickName`, `bio` FROM `profile`")
////        // remove the old table
////        database.execSQL("DROP TABLE `profile`")
////        // rename new table
////        database.execSQL("ALTER TABLE `profile_new` RENAME TO `profile`")
//    }
//}