package com.example.recipesapp1.ui.recipes

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp1.data.model.Recipe
import com.example.recipesapp1.data.model.RecipeResponse
import com.example.recipesapp1.repository.RecipesRepository
import com.example.recipesapp1.util.NetworkUtil.Companion.hasInternetConnection
import com.example.recipesapp1.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.GET
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class RecipesViewModel@Inject constructor(
    private val recipesRepository: RecipesRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val recipes: MutableLiveData<Resource<List<RecipeResponse>>> = MutableLiveData()
    var recipesResponse: List<RecipeResponse>? = null

    init {
        getRecipesRemote()
    }

    fun getRecipesRemote() = viewModelScope.launch {
        safeRecipesCall()
    }

    private suspend fun safeRecipesCall(){
        recipes.postValue(Resource.Loading())

        //проверка на наличие интернета
        //если есть - заполнения листа и локального хранилища
        try{
            if(hasInternetConnection(context)){
                val response = recipesRepository.getRecipesRemote()
                val resource = handleRecipesResponse(response)

                recipes.postValue(resource)

                clearLocal()
                resource.data?.let { recipesResponse ->
                    recipesResponse.forEach {
                        insertRecipeLocal(recipeResponseToRecipe(it))
                    }
                }
            }
            else{
                recipes.postValue(Resource.Error("No Internet Connection"))
            }
        }
        catch (ex : Exception){
            when(ex){
                is IOException -> recipes.postValue(Resource.Error("Network Failure"))
                else -> recipes.postValue(Resource.Error("Conversion Error: ${ex.message}"))
            }
        }
    }

    private fun handleRecipesResponse(response: Response<List<RecipeResponse>>): Resource<List<RecipeResponse>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
//                if (recipesResponse == null)
//                    recipesResponse = resultResponse
//                else {
//                    val oldRecipes = recipesResponse
//                    val newRecipes = resultResponse
//                    oldRecipes?.addAll(newRecipes)
//                }
                return Resource.Success(recipesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun insertRecipeLocal(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            recipesRepository.insertRecipeLocal(recipe)
        }
    }

    private fun clearLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            recipesRepository.clearLocal()
        }
    }

    fun getRecipesLocal(): LiveData<List<Recipe>> {
        return recipesRepository.getAllRecipesLocal()
    }

    fun recipeResponseToRecipe(recipeResponse: RecipeResponse) : Recipe {
        return Recipe(
            id = recipeResponse.id,
            calories = recipeResponse.calories,
            carbos = recipeResponse.carbos,
            description = recipeResponse.description,
            difficulty = recipeResponse.difficulty,
            fats = recipeResponse.fats,
            headline = recipeResponse.headline,
            image = recipeResponse.image,
            name = recipeResponse.name,
            proteins = recipeResponse.proteins,
            thumb = recipeResponse.image,
            time = recipeResponse.time
        )
    }
}