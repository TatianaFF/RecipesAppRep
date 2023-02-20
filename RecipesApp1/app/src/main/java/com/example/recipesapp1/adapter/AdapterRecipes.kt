package com.example.recipesapp1.adapter

import android.R.string
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.number.NumberFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp1.R
import com.example.recipesapp1.data.model.Recipe
import com.example.recipesapp1.data.model.RecipeResponse
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recipe.view.*


class AdapterRecipe: RecyclerView.Adapter<AdapterRecipe.RecipeViewHolder>() {
    private var listRecipes = emptyList<Recipe>()

    var onImageClick: ((Recipe) -> (Unit))? = null

    interface Callback {
        fun onImageClicked(imagePath: string?)
    }

    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return AdapterRecipe.RecipeViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val calories = holder.itemView.tv_calories
        val carbos = holder.itemView.tv_carbos
        val description = holder.itemView.tv_description
        val difficulty = holder.itemView.tv_difficulty
        val fats = holder.itemView.tv_fats
        val headline = holder.itemView.tv_headline_recipe
        val name = holder.itemView.tv_name
        val proteins = holder.itemView.tv_proteins
        val time = holder.itemView.tv_time
        val thumb_recipe = holder.itemView.thumb_recipe

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
            .into(thumb_recipe)

        thumb_recipe.setOnClickListener{
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