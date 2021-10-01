package com.example.paperairplane

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paperairplane.databinding.ActivityMainBinding
import com.google.vr.sdk.widgets.pano.VrPanoramaView
import android.graphics.BitmapFactory

import android.net.Uri
import android.os.Handler
import android.util.Log
import android.util.Pair
import com.example.paperairplane.`interface`.MainOptionClickInterface
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager

import java.io.IOException
import java.io.InputStream




class MainActivity : AppCompatActivity(), MainOptionClickInterface{
    private var view: VrPanoramaView? = null
    private var flag2=0
    private lateinit var mainBinding: ActivityMainBinding
    var sampleImages = intArrayOf(
        R.drawable.aicenter,R.drawable.chungmu,R.drawable.gwanggae
    )
    var converSampleImages = arrayListOf(
        "aicenter.jpg","chungmu.jpg","gwanggae.jpg"
    )
    val title= arrayListOf(
        "AI융합센터","영실관/충무관/율곡관","광개토관"
    )
    private var adapter=MainAdapter(this,sampleImages,title)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding= ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)
        view=mainBinding.vrPanoramaView
        panoramaMethod()
        val carouselRecyclerview = mainBinding.carousel
        carouselRecyclerview.adapter = adapter
        val carouselLayoutManager = carouselRecyclerview.getCarouselLayoutManager()
        val currentlyCenterPosition = carouselRecyclerview.getSelectedPosition()
        carouselRecyclerview.setItemSelectListener(object : CarouselLayoutManager.OnSelected {
            override fun onItemSelected(position: Int) {
                Log.d("TAG", "onItemSelected: $position")
                showSpherePanorama(Pair(intent.data, VrPanoramaView.Options()),converSampleImages[position])
                mainBinding.mainSchoolTitle.text=title[position]
            }
        })
        val dialog=MainOptionDialog(this,this)

        mainBinding.mainHomeOption.setOnClickListener { //다이얼로그 키지않은상태
            if(flag2==0){
                dialog.myDig()
                dialog.show()
                dialog.setOnDismissListener {
                    Log.d("TAG", "onCreate: dismiss1")
                }
            }
            else{
                dialog.dismiss()
                click(0)
                dialog.setOnDismissListener {
                    Log.d("TAG", "onCreate: dismiss2")
                }
            }

        }
    }
    private fun panoramaMethod() {
        showSpherePanorama(Pair(intent.data, VrPanoramaView.Options()),converSampleImages[0])
        mainBinding.mainSchoolTitle.text=title[0]
    }

    private fun showSpherePanorama(pair: Pair<Uri?, VrPanoramaView.Options>,file:String) {
        Handler().postDelayed({
            var `is`: InputStream? = null
            val assetManager=assets
            try {
                `is` = assetManager.open(file)
                val options = VrPanoramaView.Options()
                options.inputType = VrPanoramaView.Options.TYPE_MONO
                view!!.loadImageFromBitmap(BitmapFactory.decodeStream(`is`), options)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (`is` != null) {
                    try {
                        `is`.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }, 0)
    }

    override fun click(flag: Int) {
        //flag2=flag
        Log.d("TAG", "main activity get flag $flag")
        if(flag==0){
            flag2=flag
            mainBinding.mainHomeOption.setBackgroundResource(R.drawable.home_options)
        }
        if(flag==1){
            flag2=flag
            mainBinding.mainHomeOption.setBackgroundResource(R.drawable.home_options_on)
        }
        //동아리
        if(flag==2){

        }
        //세종맛집
        if(flag==3){

        }
        //지도
        if(flag==4){
            val intent= Intent(this,MapActivity::class.java)
            startActivity(intent)
        }
        //faq
        if(flag==5){
            val intent= Intent(this,FaqActivity::class.java)
            startActivity(intent)
        }
        //오시는길
        if(flag==6){
            val intent= Intent(this,StreetActivity::class.java)
            startActivity(intent)
        }
        //설정
        if(flag==7){

        }
    }

}