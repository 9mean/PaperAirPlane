package com.example.paperairplane

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.widget.RatingBar
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.paperairplane.data.Food
import com.example.paperairplane.databinding.ActivityAddFoodBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import java.text.SimpleDateFormat
import java.util.*

class AddFoodActivity : AppCompatActivity() {
    var PICK_IMAGE_FROM_ALBUM = 0
    var photoUri: Uri? = null
    var storage: FirebaseStorage?=null
    var firestore: FirebaseFirestore?=null
    lateinit var activityAddFoodBinding: ActivityAddFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddFoodBinding= ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(activityAddFoodBinding.root)
        storage= FirebaseStorage.getInstance()
        firestore= FirebaseFirestore.getInstance()
        activityAddFoodBinding.addFoodBackBtn.setOnClickListener {
            finish()
        }
        activityAddFoodBinding.addFoodImg.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
            Log.d("TAG", "onClick: upload room img")
        }
        activityAddFoodBinding.foodUploadBtn.setOnClickListener{
            activityAddFoodBinding.animationView.playAnimation()
            uploadFirebase()
        }
        activityAddFoodBinding.addFoodRating.setOnRatingBarChangeListener(object:RatingBar.OnRatingBarChangeListener{
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
                ratingBar!!.rating=rating
                Log.d("TAG", "Rating $rating  $fromUser")
            }

        })
        activityAddFoodBinding.addFoodRating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
           ratingBar.rating=rating
            Log.d("TAG", "Rating $rating  $fromUser")
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                //This is path to the selected image
//                photoUri = data?.data
//                Log.d("TAG", "photouri ${photoUri}")
//                upload_room_img.setImageURI(photoUri)
                CropImage.activity(data?.data)
                    .setFixAspectRatio(true)
                    .setMinCropResultSize(700, 700)
                    .setMaxCropResultSize(2000, 2000)
                    .start(this)
            }
//            else {
//                //Exit the addphotoactivity if you leave the album without selecting it
//                Toast.makeText(this.context, "사진을 등록하여주세요", Toast.LENGTH_SHORT).show()
//                return
//            }
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
            if (resultCode == AppCompatActivity.RESULT_OK) {
                // save photo
                photoUri = result.uri
                activityAddFoodBinding.addFoodImg.setImageURI(photoUri)
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                //showResultDialog("사진을 선택하는 도중 에러가 발생했습니다 :(")
               // ResultDialog.getDialog(requireActivity(), "사진을 선택하는 도중 에러가 발생했습니다 :(", false).show()
            }
        }
    }

    fun uploadFirebase(){
        var timestamp= SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName="IMAGE" + timestamp + ".jpeg"
        var storageRef=storage?.reference?.child("item_images")?.child(imageFileName)
        //Callback method
        val putFileJob=storageRef?.putFile(photoUri!!)
        storageRef?.putFile(photoUri!!)?.addOnSuccessListener {
            Log.d("TAG","photoUri $photoUri")
            storageRef.downloadUrl.addOnSuccessListener { uri->
                Log.d("TAG","uri $uri")
                val foodRef= firestore?.collection("food")?.document()
                var food = Food()
                food.title = activityAddFoodBinding.foodTitleEditText.text.toString()
                food.picture = uri.toString()
                food.rate=activityAddFoodBinding.addFoodRating.numStars
                food.content=activityAddFoodBinding.foodContentEditText.text.toString()
                foodRef?.set(food)
                Log.d("TAG", "등록완료")
                activityAddFoodBinding.animationView.cancelAnimation()
                activityAddFoodBinding.animationView.visibility=GONE
                FoodActivity.instance.loadData()
                finish()
            }
        }
    }
}