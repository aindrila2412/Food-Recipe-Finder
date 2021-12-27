package com.example.androidstudyjam.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidstudyjam.R
import com.example.androidstudyjam.R.layout
import com.example.androidstudyjam.databinding.FragmentHomeBinding
import com.example.androidstudyjam.datasource.model.FoodRecipe
import com.example.androidstudyjam.datasource.model.RecipeResult
import com.example.androidstudyjam.ui.home.extensionfunc.collectOnLifeCycleScope
import com.example.androidstudyjam.ui.home.extensionfunc.getQueryTextChangeStateFlow
import com.example.androidstudyjam.ui.home.extensionfunc.hide
import com.example.androidstudyjam.ui.home.extensionfunc.hideKeyboard
import com.example.androidstudyjam.ui.home.extensionfunc.show
import com.example.androidstudyjam.ui.home.extensionfunc.snack
import com.example.androidstudyjam.viewmodel.RecipeViewModel
import com.example.moviesshow.arch.ViewState
import com.example.moviesshow.arch.ViewState.Empty
import com.example.moviesshow.arch.ViewState.Error
import com.example.moviesshow.arch.ViewState.Loading
import com.example.moviesshow.arch.ViewState.Success
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest

class HomeFragment : Fragment(layout.fragment_home) {

    private val recipeViewModel: RecipeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        initView()
    }



    private fun initView() {
        setUpRecipeAdapter()
        searchRecipe()
    }

    private fun setUpRecipeAdapter() {

        recipeAdapter = RecipeAdapter {

            val direction = HomeFragmentDirections.actionHomeFragment2ToRecipeDetailFragment(it)

            findNavController().navigate(direction)

        }

        binding.recipeListRv.adapter = recipeAdapter
    }

    private fun searchRecipe() {
       viewLifecycleOwner.collectOnLifeCycleScope(
            binding.recipeSearchView.getQueryTextChangeStateFlow()
                .debounce(1000)
                .filter {
                    (it.isNotEmpty())
                }.flatMapLatest {
                    recipeViewModel.searchFood(it)
                }
        ) { recipeEvent ->

            dataObserver(recipeEvent)

        }
    }


    private fun dataObserver(recipeEvent: ViewState<List<RecipeResult>>) {

        when (recipeEvent) {
            is Loading -> {
                binding.progressCircular.visibility = View.VISIBLE
                binding.recipeListRv.hide()
                binding.errorTv.hide()
            }
            is Success -> {
                binding.progressCircular.visibility = View.GONE
                binding.errorTv.hide()
                binding.recipeListRv.show()
                binding.recipeIcon.hide()
                recipeAdapter.submitList(recipeEvent.item)
                binding.root.hideKeyboard()

            }
            is Empty -> {
                binding.progressCircular.visibility = View.GONE
                binding.errorTv.show()
                binding.errorTv.text = getString(R.string.no_found)
                binding.root.hideKeyboard()
                binding.recipeIcon.show()
            }
            is Error -> {
                binding.progressCircular.visibility = View.GONE
                binding.recipeListRv.hide()
                recipeEvent.errorMsg.snack(Color.RED, binding.root)
                binding.errorTv.show()
                binding.errorTv.text = recipeEvent.errorMsg
                binding.root.hideKeyboard()

            }

        }
    }

}