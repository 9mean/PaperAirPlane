package com.example.paperairplane.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paperairplane.R
import com.example.paperairplane.data.Food
import com.example.paperairplane.data.Review
import kotlin.math.roundToInt

//class DetailFoodAdapter(val context: Context, val reviewList: String?): RecyclerView.Adapter<DetailFoodAdapter.ViewHolder>()  {
//    inner class ViewHolder(
//        itemView: View
//    ) : RecyclerView.ViewHolder(itemView) {
//        val itemUserName=itemView.findViewById<TextView>(R.id.review_user_name)
//        val itemContent=itemView.findViewById<TextView>(R.id.review_content)
//        fun bind(data: Review) {
//            itemContent.text=data.content
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return reviewList?.size ?: 0
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewHolder = ViewHolder(
//        LayoutInflater.from(parent.context).inflate(R.layout.item_review_list, parent, false))
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        reviewList?.get(position)?.let {
//            holder.bind(it)
//            holder.itemUserName.text="종이"+(position+1).toString()
//        }
//
//    }
//}