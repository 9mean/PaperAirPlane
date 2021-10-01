package com.example.paperairplane.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paperairplane.R

class MainAdapter(private val context: Context, private val list: IntArray,private val title:ArrayList<String>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.school_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position],position)
        Log.d("TAG", "bind: $position")
    }
    inner class  ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val schoolImg: ImageView = itemView.findViewById(R.id.school_img)
        private val schoolTitle: TextView = itemView.findViewById(R.id.school_text)
        fun bind(position: Int,target:Int) {
            schoolImg.setImageResource(position)

            schoolTitle.text=title[target]
        }
    }
}