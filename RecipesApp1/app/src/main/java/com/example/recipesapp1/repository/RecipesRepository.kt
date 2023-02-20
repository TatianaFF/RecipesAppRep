package com.example.recipesapp1.repository

import com.example.recipesapp1.data.local.RecipeDao
import com.example.recipesapp1.data.model.Recipe
import com.example.recipesapp1.data.model.RecipeResponse
import com.example.recipesapp1.data.remote.RecipesApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipesRepository@Inject constructor(
    private val recipesApi: RecipesApi,
    private val recipeDao: RecipeDao
) {
    suspend fun getRecipesRemote(): Response<List<RecipeResponse>> {  //Response<RecipesResponse>
        return recipesApi.getRecipesRemote()
    }

    fun getAllRecipesLocal() = recipeDao.getAllRecipes()

    suspend fun insertRecipeLocal(recipe: Recipe) = recipeDao.insertRecipe(recipe)

    suspend fun clearLocal() = recipeDao.clearTable()
}