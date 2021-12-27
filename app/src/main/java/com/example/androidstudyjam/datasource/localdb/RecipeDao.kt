package com.example.androidstudyjam.datasource.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.androidstudyjam.datasource.model.RecipeResult

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipes: List<RecipeResult>)

    @Query("Select * from recipe where searchQuery=:q")
    suspend fun getRecipe(q: String): List<RecipeResult>

    @Query("DELETE FROM recipe")
    suspend fun deleteAllRecipe()

    @Transaction
    suspend fun replace(newItem: List<RecipeResult>) {
        deleteAllRecipe()
        insert(newItem)
    }

}