package com.example.paperairplane

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paperairplane.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    lateinit var activityMapBinding: ActivityMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMapBinding= ActivityMapBinding.inflate(layoutInflater)
        setContentView(activityMapBinding.root)
        activityMapBinding.mapBackBtn.setOnClickListener {
            finish()
        }
    }
}