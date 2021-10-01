package com.example.paperairplane.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.paperairplane.DetailFoodActivity
import com.example.paperairplane.R
import com.example.paperairplane.data.Food
import kotlin.math.roundToInt

class FoodAdapter(val context:Context,val foodList:ArrayList<Food>): RecyclerView.Adapter<FoodAdapter.ViewHolder>()  {
    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val itemTitle=itemView.findViewById<TextView>(R.id.food_title)
        val itemImg=itemView.findViewById<ImageView>(R.id.food_img)
        val itemContent=itemView.findViewById<TextView>(R.id.food_content)
        val itemRating=itemView.findViewById<RatingBar>(R.id.food_rating)
        fun bind(data: Food) {
            itemTitle.text=data.title
            itemContent.text=data.content
            itemRating.rating= data.rate!!.toDouble().roundToInt().toFloat()
            Glide.with(itemView.context)
                .load(data.picture)
                .transform(CenterCrop(), RoundedCorners(50))
                .into(itemImg)

        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_food_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foodList[position])
        holder.itemView.setOnClickListener {
                Log.d("TAG", "click 음식 ${foodList[position]} ")
                val intent= Intent(context, DetailFoodActivity::class.java)
                intent.putExtra("food",foodList[position])
                holder.itemView.context.startActivity(intent)
        }
    }
}