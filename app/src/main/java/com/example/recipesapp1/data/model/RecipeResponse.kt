package com.example.recipesapp1.data.model

import java.io.Serializable

data class RecipeResponse(
    var id: String = "",
    var calories: String?,
    var carbos: String?,
    var description: String?,
    var difficulty: Int?,
    var fats: String?,
    var headline: String?,
    var image: String? = null,
    var name: String?,
    var proteins: String?,
    var thumb: String? = null,
    var time: String?

): Serializable