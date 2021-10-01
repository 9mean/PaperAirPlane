package com.example.paperairplane

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paperairplane.data.FAQ
import com.example.paperairplane.databinding.ActivityFaqBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class FaqActivity : AppCompatActivity() {
    private lateinit var faqList: List<FAQ>
    private lateinit var adapter: FaqExpandableAdapter
    lateinit var activityFaqBinding: ActivityFaqBinding
    var firestore: FirebaseFirestore?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFaqBinding= ActivityFaqBinding.inflate(layoutInflater)
        setContentView(activityFaqBinding.root)
        activityFaqBinding.faqBackBtn.setOnClickListener {
            finish()
        }
        val recyclerView = activityFaqBinding.faqRecyclerView

        faqList = ArrayList()
        faqList = loadData()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FaqExpandableAdapter(faqList)
        recyclerView.adapter = adapter

    }


    private fun loadData(): List<FAQ> {
        val faq = ArrayList<FAQ>()
        firestore= FirebaseFirestore.getInstance()
        firestore!!.collection("qna")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    faq.add(document.toObject(FAQ::class.java))
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
        Log.d("TAG", "Faq $faq ")
        return faq
    }

}