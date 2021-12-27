package com.example.androidstudyjam.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstudyjam.datasource.localdb.RecipeDatabase
import com.example.androidstudyjam.datasource.model.RecipeResult
import com.example.androidstudyjam.datasource.repository.RecipeRepositoryImpl
import com.example.androidstudyjam.network.RetrofitFactory
import com.example.moviesshow.arch.Resource
import com.example.moviesshow.arch.ViewState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class RecipeViewModel(context: Application) : AndroidViewModel(context) {

    private val repository = RecipeRepositoryImpl(RetrofitFactory, RecipeDatabase.getInstance(context).getRecipeDao())

    fun searchFood(foodName: String, recipeCount: Int = 50): StateFlow<ViewState<List<RecipeResult>>> {
        return flow {

            when (val result = repository.searchRecipe(foodName, recipeCount)) {
                is Resource.Success -> {

                    when (result.value.totalResults) {
                        0 -> {
                            emit(ViewState.Empty)
                        }
                        else -> {
                            val recipeList: List<RecipeResult> = result.value.searchResults[0].recipeResults

                            recipeList.map {
                                it.searchQuery = foodName
                            }
                            repository.insert(recipeList)
                            emit(ViewState.Success(recipeList))
                        }
                    }
                }
                is Resource.Error -> {
                    emit(ViewState.Error(result.msg))
                    val localList = repository.getRecipeFromLocal(foodName)
                    if (localList.isNotEmpty()) {
                        emit(ViewState.Success(localList))
                    } else {
                        emit(ViewState.Empty)
                    }

                }
            }

        }.stateIn(
            initialValue = ViewState.Loading,
            scope = viewModelScope,
            // it tell the flow active only for 5 second if there is no collector
            started = SharingStarted.WhileSubscribed(5000)
        )

    }

}