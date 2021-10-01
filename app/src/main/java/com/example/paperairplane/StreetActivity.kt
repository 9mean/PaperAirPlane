package com.example.paperairplane

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paperairplane.databinding.ActivityStreetBinding

class StreetActivity : AppCompatActivity() {
    lateinit var activityStreetBinding: ActivityStreetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityStreetBinding= ActivityStreetBinding.inflate(layoutInflater)
        setContentView(activityStreetBinding.root)

        activityStreetBinding.streetBackBtn.setOnClickListener {
            finish()
        }
    }
}