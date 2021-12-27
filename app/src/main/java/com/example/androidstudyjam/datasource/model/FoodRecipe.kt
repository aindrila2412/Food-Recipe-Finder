package com.example.androidstudyjam.datasource.model

import com.google.gson.annotations.SerializedName

data class FoodRecipe(

    @SerializedName("searchResults")
    val searchResults: List<SearchResult>,
    @SerializedName("totalResults")
    val totalResults: Int
) {


    data class SearchResult(
        @SerializedName("name")
        val name: String,
        @SerializedName("results")
        val recipeResults: List<RecipeResult>,
        @SerializedName("totalResults")
        val totalResults: Int,
        @SerializedName("totalResultsVariants")
        val totalResultsVariants: Int,
        @SerializedName("type")
        val type: String
    ) {



    }
}