package com.example.recipesapp1.adapter

import android.R.string
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.number.NumberFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp1.R
import com.example.recipesapp1.data.model.Recipe
import com.example.recipesapp1.data.model.RecipeResponse
import com.example.recipesapp1.databinding.ItemRecipeBinding
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso


class AdapterRecipe: RecyclerView.Adapter<AdapterRecipe.RecipeViewHolder>() {
    lateinit var calories: TextView
    lateinit var carbos: TextView
    lateinit var description: TextView
    lateinit var difficulty: TextView
    lateinit var fats: TextView
    lateinit var headline: TextView
    lateinit var name: TextView
    lateinit var proteins: TextView
    lateinit var time: TextView
    lateinit var thumb: ImageView

    private var listRecipes = emptyList<Recipe>()

    var onImageClick: ((Recipe) -> (Unit))? = null

    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemRecipeBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return AdapterRecipe.RecipeViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        with(holder.binding){
            calories = tvCalories
            carbos = tvCarbos
            description = tvDescription
            difficulty = tvDifficulty
            fats = tvFats
            headline = tvHeadlineRecipe
            name = tvName
            proteins = tvProteins
            time = tvTime
            thumb = thumbRecipe
        }

        val recipeCurrent = listRecipes[position]

        calories.text = recipeCurrent.calories
        carbos.text = recipeCurrent.carbos
        description.text = recipeCurrent.description
        difficulty.text = recipeCurrent.difficulty.toString()
        fats.text = recipeCurrent.fats
        headline.text = recipeCurrent.headline
        name.text = recipeCurrent.name
        proteins.text = recipeCurrent.proteins
        time.text = recipeCurrent.time

        //картинка с кэша
        Picasso.get()
            .load(recipeCurrent.thumb)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(thumb)

        thumb.setOnClickListener{
            onImageClick?.invoke(recipeCurrent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListRecipes(list: List<Recipe>){
        listRecipes = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listRecipes.size
    }
}