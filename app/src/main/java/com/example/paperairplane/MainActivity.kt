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
import android.view.View
import android.widget.LinearLayout
import com.example.paperairplane.`interface`.MainOptionClickInterface
import com.example.paperairplane.adapter.MainAdapter
import com.example.paperairplane.util.BildingConstant
import com.example.paperairplane.util.ToggleAnimation
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager

import java.io.IOException
import java.io.InputStream




class MainActivity : AppCompatActivity(), MainOptionClickInterface{
    private var view: VrPanoramaView? = null
    private var flag2=0
    private var toogleFlag=false
    private var currentPosition=0
    private lateinit var mainBinding: ActivityMainBinding
    var sampleImages = intArrayOf(
        R.drawable.frontdoor,R.drawable.front,R.drawable.aicenter,R.drawable.chungmu,R.drawable.gwanggae,R.drawable.zipheon,R.drawable.zeokdoor,R.drawable.ujeong,R.drawable.sejonggwan,R.drawable.moza,R.drawable.hakjeong,R.drawable.gunja,R.drawable.eziheon,R.drawable.edang,R.drawable.dasan,R.drawable.backdoor,R.drawable.gisuk,
    )
    var converSampleImages = arrayListOf(
        "frontdoor.jpg","front.jpg","aicenter.jpg","chungmu.jpg","gwanggae.jpg","zipheon.jpg","zeokdoor.jpg","ujeong.jpg","sejonggwan.jpg","moza.jpg","hakjeong.jpg","gunja.jpg","eziheon.jpg","eongduk.jpg","edang.jpg","dasan.jpg","backdoor.jpg","gisuk.jpg"
    )
    val title= arrayListOf(
        "정문","학생회관/대양홀","AI융합센터","영실관/충무관/율곡관","광개토관","집현관","쪽문","우정당","세종관","모짜르트홀","학술정보원","군자관","애지헌","용덕관","이당관","다산관","후문","기숙사"
    )
    private var adapter= MainAdapter(this,sampleImages,title)
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
                toogleFlag=false
                currentPosition=position
                mainBinding.mainContent.text=""
                toggleLayout(toogleFlag,mainBinding.mainDownBtn, mainBinding.layoutExpand)
                Log.d("TAG", "onItemSelected: $position")
                showSpherePanorama(Pair(intent.data, VrPanoramaView.Options()),converSampleImages[position])
                mainBinding.mainSchoolTitle.text=title[position]

            }
        })
        mainBinding.mainDownBtn.setOnClickListener {
            mainBinding.layoutExpand.setBackgroundColor(resources.getColor(android.R.color.transparent))
            when(currentPosition){
                0->{
                    mainBinding.mainContent.text=BildingConstant.FRONT_DOOR
                }
                1->{
                    mainBinding.mainContent.text=BildingConstant.FRONT
                }
                2->{
                    mainBinding.mainContent.text=BildingConstant.AI_CENTER
                }
                3->{
                    mainBinding.mainContent.text=BildingConstant.CHUNG_MU
                }
                4->{
                    mainBinding.mainContent.text=BildingConstant.GWANG_GAE
                }
                5->{
                    mainBinding.mainContent.text=BildingConstant.ZIP_HEON
                }
                6->{
                    mainBinding.mainContent.text=BildingConstant.ZEOK_DOOR
                }
                7->{
                    mainBinding.mainContent.text=BildingConstant.UJEONG
                }
                8->{
                    mainBinding.mainContent.text=BildingConstant.SEJONG
                }
                9->{
                    mainBinding.mainContent.text=BildingConstant.MOZA
                }
                10->{
                    mainBinding.mainContent.text=BildingConstant.HAK_JEONG
                }
                11->{
                    mainBinding.mainContent.text=BildingConstant.GUNJA
                }
                12->{
                    mainBinding.mainContent.text=BildingConstant.EJIHEON
                }
                13->{
                    mainBinding.mainContent.text=BildingConstant.EONGDEOK
                }
                14->{
                    mainBinding.mainContent.text=BildingConstant.EDANG
                }
                15->{
                    mainBinding.mainContent.text=BildingConstant.DASAN
                }
                16->{
                    mainBinding.mainContent.text=BildingConstant.BACKDOOR
                }
                17->{
                    mainBinding.mainContent.text=BildingConstant.GISUK
                }
            }
            toggleLayout(!toogleFlag,it, mainBinding.layoutExpand)
        }

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
    private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
        // 2
        ToggleAnimation.toggleArrow(view, isExpanded)
        if (isExpanded) {
            ToggleAnimation.expand(layoutExpand)
            toogleFlag=true
        } else {
            ToggleAnimation.collapse(layoutExpand)
            toogleFlag=false
        }
        return isExpanded
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
            val intent= Intent(this,FoodActivity::class.java)
            startActivity(intent)
        }
        //지도
        if(flag==4){
            val intent= Intent(this,MapActivity::class.java)
            startActivity(intent)
        }
        //faq
        if(flag==5){
            val intent= Intent(this, FaqActivity::class.java)
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