package com.example.recipesapp1.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipesapp1.data.model.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeModel: Recipe)

    @Query("select * from recipe_table")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("DELETE FROM recipe_table")
    suspend fun clearTable()
}