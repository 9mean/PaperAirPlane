package com.example.paperairplane

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.paperairplane.data.Review
import com.example.paperairplane.databinding.ActivityReviewBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ReviewActivity : AppCompatActivity() {
    lateinit var reviewBinding: ActivityReviewBinding
    var firestore: FirebaseFirestore?=null
    lateinit var uuid:String
    var reviewList=ArrayList<Review>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore= FirebaseFirestore.getInstance()
        uuid= intent.getStringExtra("uuid").toString()
//        if(intent.getSerializableExtra("review")!=null){
//            reviewList= intent.getSerializableExtra("review") as Review
//        }

        Log.d("TAG", "Review list $reviewList uuid $uuid")
        reviewBinding= ActivityReviewBinding.inflate(layoutInflater)
        setContentView(reviewBinding.root)
        reviewBinding.reviewBackBtn.setOnClickListener {
            finish()
        }
        reviewBinding.reviewUploadBtn.setOnClickListener {
            uploadFirebase()

        }
    }

    private fun uploadFirebase() {
        val review=Review()
        reviewBinding.animationView.visibility= VISIBLE
        reviewBinding.animationView.playAnimation()
        review.content=reviewBinding.reviewContent.text.toString()
        review.password="1"
        review.username="test"
        reviewList.add(review)
        firestore?.collection("food")?.document(uuid)
            ?.update("review", FieldValue.arrayUnion(review))?.addOnCompleteListener {
                if(it.isSuccessful){
                    Log.d("TAG", "uploadFirebase: ")
                    reviewBinding.animationView.visibility=GONE
                    finish()
                }
                Log.d("TAG", "uploadFirebase it: $it")
            }
    }
}