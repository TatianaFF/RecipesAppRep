package com.example.recipesapp1.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.io.Serializable

data class RecipeResponse(
    @PrimaryKey
    var id: String = "",

    @ColumnInfo
    var calories: String?,

    @ColumnInfo
    var carbos: String?,

    @ColumnInfo
    var description: String?,

    @ColumnInfo
    var difficulty: Int?,

    @ColumnInfo
    var fats: String?,

    @ColumnInfo
    var headline: String?,

    @ColumnInfo
    var image: String? = null,

    @ColumnInfo
    var name: String?,

    @ColumnInfo
    var proteins: String?,

    @ColumnInfo
    var thumb: String? = null,

    @ColumnInfo
    var time: String?,

): Serializable