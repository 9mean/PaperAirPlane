package com.example.paperairplane

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paperairplane.adapter.FaqExpandableAdapter
import com.example.paperairplane.data.FAQ
import com.example.paperairplane.databinding.ActivityFaqBinding
import com.google.firebase.firestore.FirebaseFirestore

class FaqActivity : AppCompatActivity() {
    private lateinit var faqList: ArrayList<FAQ>
    private lateinit var adapter: FaqExpandableAdapter
    lateinit var activityFaqBinding: ActivityFaqBinding
    private lateinit var recyclerView:RecyclerView
    var firestore: FirebaseFirestore?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFaqBinding= ActivityFaqBinding.inflate(layoutInflater)
        setContentView(activityFaqBinding.root)
        activityFaqBinding.faqBackBtn.setOnClickListener {
            finish()
        }
        activityFaqBinding.animationView.playAnimation()
        recyclerView = activityFaqBinding.faqRecyclerView

        faqList = ArrayList()
        loadData()

    }


    private fun loadData(){
        firestore= FirebaseFirestore.getInstance()
        firestore!!.collection("qna")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    faqList.add(document.toObject(FAQ::class.java))
                }
                Log.d("TAG", "$faqList")
                activityFaqBinding.animationView.cancelAnimation()
                activityFaqBinding.animationView.visibility=GONE
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(this@FaqActivity)
                adapter = FaqExpandableAdapter(faqList)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }

}