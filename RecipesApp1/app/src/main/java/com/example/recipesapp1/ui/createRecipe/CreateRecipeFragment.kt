package com.example.recipesapp1.ui.createRecipe

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipesapp1.R

class CreateRecipeFragment : Fragment() {

    companion object {
        fun newInstance() = CreateRecipeFragment()
    }

    private lateinit var viewModel: CreateRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_recipe, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateRecipeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}