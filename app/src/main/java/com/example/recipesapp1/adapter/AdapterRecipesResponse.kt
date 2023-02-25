package com.example.recipesapp1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp1.R
import com.example.recipesapp1.data.model.RecipeResponse
import com.example.recipesapp1.databinding.ItemRecipeBinding
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso


class AdapterRecipesResponse: RecyclerView.Adapter<AdapterRecipesResponse.RecipeResponseViewHolder>() {
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

    private var listRecipesResponce = emptyList<RecipeResponse>()

    var onImageClick: ((RecipeResponse) -> (Unit))? = null   //adapter.onItemClick = { mode ->

    class RecipeResponseViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemRecipeBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeResponseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return AdapterRecipesResponse.RecipeResponseViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecipeResponseViewHolder, position: Int) {
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
            .into(thumb)

        Picasso.get()
            .load(recipeCurrent.image)
            .networkPolicy(NetworkPolicy.NO_CACHE)

        thumb.setOnClickListener{
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