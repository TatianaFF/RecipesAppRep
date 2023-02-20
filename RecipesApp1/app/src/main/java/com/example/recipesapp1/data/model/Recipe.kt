package com.example.recipesapp1.data.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "recipe_table")
data class Recipe(
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

    @ColumnInfo//(typeAffinity = ColumnInfo.Companion.BLOB)
    var image: String? = null,   //ByteArray

    @ColumnInfo
    var name: String?,

    @ColumnInfo
    var proteins: String?,

    @ColumnInfo//(typeAffinity = ColumnInfo.Companion.BLOB)
    var thumb: String? = null,

    @ColumnInfo
    var time: String?,
): Serializable