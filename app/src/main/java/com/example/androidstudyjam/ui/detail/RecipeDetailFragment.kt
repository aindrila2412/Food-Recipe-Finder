package com.example.androidstudyjam.ui.detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.androidstudyjam.R
import com.example.androidstudyjam.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment(R.layout.fragment_recipe_detail) {

    private val args: RecipeDetailFragmentArgs by navArgs()

    lateinit var binding: FragmentRecipeDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecipeDetailBinding.bind(view)

        init(args)
    }

    fun init(recipeArgs: RecipeDetailFragmentArgs) {

        binding.contentTv.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(recipeArgs.recipe.content, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(recipeArgs.recipe.content)
        }

        Glide.with(binding.iv.context).load(recipeArgs.recipe.image).into(binding.iv)

    }

}