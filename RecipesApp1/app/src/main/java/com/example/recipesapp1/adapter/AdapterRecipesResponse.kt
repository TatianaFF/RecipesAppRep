package com.example.recipesapp1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp1.R
import com.example.recipesapp1.data.model.RecipeResponse
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recipe.view.*


class AdapterRecipesResponse: RecyclerView.Adapter<AdapterRecipesResponse.RecipeResponseViewHolder>() {
    private var listRecipesResponce = emptyList<RecipeResponse>()

    var onImageClick: ((RecipeResponse) -> (Unit))? = null   //adapter.onItemClick = { mode ->

    class RecipeResponseViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeResponseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return AdapterRecipesResponse.RecipeResponseViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecipeResponseViewHolder, position: Int) {
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

        val recipeCurrent = listRecipesResponce[position]

        calories.text = recipeCurrent.calories
        carbos.text = recipeCurrent.carbos
        description.text = recipeCurrent.description
        difficulty.text = recipeCurrent.difficulty.toString()
        fats.text = recipeCurrent.fats
        headline.text = recipeCurrent.headline
        name.text = recipeCurrent.name
        proteins.text = recipeCurrent.proteins
        time.text = recipeCurrent.time

        //в данном случае можно было сделать один адаптер и не преобразовывать модель с api для загрузки в локальную БД
        //но я не уверена что с помощью Picasso кэшировать картинки хорошая идея
        //думаю, лучше скачивать картинки при наличии соединения и в room хранить путь к картинке, либо ByteArray
        Picasso.get()
            .load(recipeCurrent.thumb)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .into(thumb_recipe)

        Picasso.get()
            .load(recipeCurrent.image)
            .networkPolicy(NetworkPolicy.NO_CACHE)

        thumb_recipe.setOnClickListener{
            onImageClick?.invoke(recipeCurrent)


        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListRecipesResponce(list: List<RecipeResponse>){
        listRecipesResponce = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listRecipesResponce.size
    }
}