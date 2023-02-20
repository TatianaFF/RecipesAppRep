package com.example.recipesapp1.ui.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp1.adapter.AdapterRecipe
import com.example.recipesapp1.adapter.AdapterRecipesResponse
import com.example.recipesapp1.databinding.FragmentRecipesBinding
import com.example.recipesapp1.util.Resource
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "RecipesFragment"
@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private lateinit var binding: FragmentRecipesBinding
    private val viewModel: RecipesViewModel by viewModels()
    var isLoading = false
    private var adapterRecipe = AdapterRecipe()
    private var adapterRecipeResponse = AdapterRecipesResponse()
    private lateinit var rvRecipes: RecyclerView
    private lateinit var fullScreenContainer: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesBinding.inflate(layoutInflater, container, false)

        rvRecipes = binding.rvRecipes
        fullScreenContainer = binding.fullScreenContainer

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //обработка нажатия на превью картинку, заполнение ImageView контейнера
        //с интернета/с кэша
        adapterRecipeResponse.onImageClick = { recipe ->
            Log.e("CLICK", recipe.toString())

            Picasso.get()
                .load(recipe.image)
                .into(fullScreenContainer)

            if (fullScreenContainer.visibility == View.GONE){
                fullScreenContainer.visibility = View.VISIBLE
            }

        }

        adapterRecipe.onImageClick = { recipe ->
            Log.e("CLICK LOCAL", recipe.toString())
            Picasso.get()
                .load(recipe.image)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(fullScreenContainer)

            if (fullScreenContainer.visibility == View.GONE){
                fullScreenContainer.visibility = View.VISIBLE
            }

        }

        fullScreenContainer.setOnClickListener {
            it.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        //если интернет есть - данные загружаются с api, загружаются в локальную БД
        //если интернета нет - берем данные из локальной БД
        viewModel.recipes.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Success -> {
                    isLoading = false
                    it.data?.let { listRecipes ->
                        rvRecipes.adapter = adapterRecipeResponse
                        adapterRecipeResponse.setListRecipesResponce(listRecipes)
                        Log.e("REMOTE DATA", listRecipes.size.toString())

                    }
                }
                is Resource.Error -> {
                    isLoading = true
                    it.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error: $message")

                        viewModel.getRecipesLocal().observe(viewLifecycleOwner){ listRecipesLocal ->
                            rvRecipes.adapter = adapterRecipe
                            adapterRecipe.setListRecipes(listRecipesLocal)
                            Log.e("LOCAL DATA", listRecipesLocal.size.toString())
                        }
                    }
                }
                is Resource.Loading -> {

                }
            }
        }
    }

}