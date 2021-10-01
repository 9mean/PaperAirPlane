package com.example.paperairplane

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paperairplane.adapter.FoodAdapter
import com.example.paperairplane.data.Food
import com.example.paperairplane.databinding.ActivityFoodBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodActivity : AppCompatActivity() {
    companion object{
        lateinit var instance:FoodActivity
    }
    lateinit var activityFoodBinding: ActivityFoodBinding
    lateinit var adapter: FoodAdapter
    lateinit var recyclerView:RecyclerView
    var foodList=ArrayList<Food>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance=this
        activityFoodBinding= ActivityFoodBinding.inflate(layoutInflater)
        setContentView(activityFoodBinding.root)
        activityFoodBinding.animationView.playAnimation()
        loadData()
        lifecycleScope.launch(Dispatchers.IO) {


            withContext(Dispatchers.Main){

            }
        }
        activityFoodBinding.foodBackBtn.setOnClickListener {
            finish()
        }
        activityFoodBinding.foodPlusBtn.setOnClickListener {
            val intent= Intent(this,AddFoodActivity::class.java)
            startActivity(intent)
        }

    }
    fun loadData(){
        Log.d("TAG", "loadData: 음식점 업로드")
        foodList.clear()
        val firestore= FirebaseFirestore.getInstance()
        firestore.collection("food")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    foodList.add(document.toObject(Food::class.java))
                }
                activityFoodBinding.animationView.cancelAnimation()
                activityFoodBinding.animationView.visibility= GONE
                recyclerView= activityFoodBinding.foodRecyclerView
                adapter= FoodAdapter(this@FoodActivity,foodList)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(this@FoodActivity)
                recyclerView.adapter=adapter
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }
}