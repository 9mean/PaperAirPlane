package com.example.paperairplane

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.paperairplane.data.Food
import com.example.paperairplane.databinding.ActivityDetailFoodBinding
import kotlin.math.roundToInt

class DetailFoodActivity : AppCompatActivity() {
    lateinit var foodData:Food
    lateinit var detailFoodBinding: ActivityDetailFoodBinding
    lateinit var recyclerView: RecyclerView
    //lateinit var adapter: DetailFoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodData = intent.getSerializableExtra("food") as Food
        detailFoodBinding= ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(detailFoodBinding.root)
        detailFoodBinding.detailFoodBackBtn.setOnClickListener {
            finish()
        }
        detailFoodBinding.detailFoodReviewBtn.setOnClickListener {
           val intent= Intent(this,ReviewActivity::class.java)
            intent.putExtra("uuid",foodData.uuid)
            intent.putExtra("review",foodData.review)
            startActivity(intent)
        }
        initLayout()

    }

    fun initLayout(){
        detailFoodBinding.detailFoodContent.text=foodData.content.toString()
        detailFoodBinding.detailFoodRating.rating=foodData.rate!!.toDouble().roundToInt().toFloat()
        Glide.with(this)
            .load(foodData.picture)
            .transform(CenterCrop(), RoundedCorners(50))
            .into(detailFoodBinding.detailFoodImg)
        detailFoodBinding.detailFoodTitle.text=foodData.title.toString()
        detailFoodBinding.detailFoodCardView.radius= 40F

        //adapter=DetailFoodAdapter(this,foodData.review)
//        recyclerView= detailFoodBinding.detailFoodRecyclerView
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter=adapter
//        adapter.notifyDataSetChanged()
    }
}