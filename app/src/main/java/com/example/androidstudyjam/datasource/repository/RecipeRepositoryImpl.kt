package com.example.androidstudyjam.datasource.repository

import android.app.DownloadManager.Query
import android.util.Log
import com.example.androidstudyjam.datasource.localdb.RecipeDao
import com.example.androidstudyjam.datasource.model.FoodRecipe
import com.example.androidstudyjam.datasource.model.RecipeResult
import com.example.androidstudyjam.network.RecipeService
import com.example.androidstudyjam.network.RetrofitFactory
import com.example.androidstudyjam.network.getResult
import com.example.moviesshow.arch.Resource

class RecipeRepositoryImpl(private val retrofitFactory: RetrofitFactory, private val dao: RecipeDao) {

    suspend fun searchRecipe(foodName: String, recipeCount: Int): Resource<FoodRecipe> {

        Log.d("apicalledddddddd", "calledddddd")

        return  getResult {
            retrofitFactory.retrofit.create(RecipeService::class.java).searchRecipe(foodName, recipeCount)
        }
    }

    suspend  fun getRecipeFromLocal(query: String): List<RecipeResult> {

       return dao.getRecipe(query)
    }


    suspend fun insert(recipeList:List<RecipeResult>){
        dao.replace(recipeList)
    }


}