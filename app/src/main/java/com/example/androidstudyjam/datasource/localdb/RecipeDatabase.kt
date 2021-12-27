package com.example.androidstudyjam.datasource.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidstudyjam.datasource.model.RecipeResult

const val db_name = "recipe_database"

@Database(entities = [RecipeResult::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao

    companion object {
        private var instance: RecipeDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): RecipeDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, RecipeDatabase::class.java, db_name
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }

    }

}