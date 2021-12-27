package com.example.androidstudyjam.network

import com.example.androidstudyjam.datasource.model.FoodRecipe
import com.example.moviesshow.arch.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {

    @GET("food/search")
    suspend fun searchRecipe(
        @Query("query") foodName: String,
        @Query("number") recipeCount: Int
    ): Response<FoodRecipe>
}