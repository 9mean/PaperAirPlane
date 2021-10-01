package com.example.paperairplane.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paperairplane.R
import com.example.paperairplane.data.FAQ
import com.example.paperairplane.util.ToggleAnimation

class FaqExpandableAdapter(
private val faqList: List<FAQ>
) : RecyclerView.Adapter<FaqExpandableAdapter.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_faq_list, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(faqList[position])
        Log.d("TAG", "onBindViewHolder: ${faqList[position]}")
    }

    override fun getItemCount(): Int {
        return faqList.size
    }
    inner class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(faq: FAQ) {
            val txtName = itemView.findViewById<TextView>(R.id.faq_title)
            val imgMore = itemView.findViewById<ImageView>(R.id.faq_down_btn)
            val layoutExpand = itemView.findViewById<LinearLayout>(R.id.layout_expand)
            val txtContent=itemView.findViewById<TextView>(R.id.faq_answer)
            txtName.text = faq.title
            txtContent.text=faq.content
            imgMore.setOnClickListener {
                // 1
                val show = toggleLayout(!faq.isExpanded, it, layoutExpand)
                faq.isExpanded = show
            }
        }

        private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
            // 2
            ToggleAnimation.toggleArrow(view, isExpanded)
            if (isExpanded) {
                ToggleAnimation.expand(layoutExpand)
            } else {
                ToggleAnimation.collapse(layoutExpand)
            }
            return isExpanded
        }
    }
}