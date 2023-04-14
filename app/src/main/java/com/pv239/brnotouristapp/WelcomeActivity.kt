package com.pv239.brnotouristapp

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import android.widget.TextView
import com.pv239.brnotouristapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var fullscreenContent: TextView
    private var isFullscreen: Boolean = false
    private val SPLASH_DELAY: Long = 2000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isFullscreen = true

        supportActionBar?.hide()
        fullscreenContent = binding.fullscreenContent

        Handler().postDelayed({
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }

}