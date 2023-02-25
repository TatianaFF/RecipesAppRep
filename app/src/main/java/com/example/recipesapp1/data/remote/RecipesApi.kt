package com.example.recipesapp1.data.remote

import com.example.recipesapp1.data.model.Recipe
import com.example.recipesapp1.data.model.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface RecipesApi {
    @GET("android-test/recipes.json")
    suspend fun getRecipesRemote(): Response<List<RecipeResponse>>  //Response<RecipesResponse>
}