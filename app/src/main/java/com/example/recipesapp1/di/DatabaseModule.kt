package com.example.recipesapp1.di

import android.app.Application
import androidx.room.Room
import com.example.recipesapp1.data.local.RecipeDao
import com.example.recipesapp1.data.local.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: RecipeDatabase.Callback): RecipeDatabase{
        return Room.databaseBuilder(application, RecipeDatabase::class.java, "recipes_database")
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideRecipesDao(db: RecipeDatabase): RecipeDao{
        return db.getRecipeDao()
    }
}