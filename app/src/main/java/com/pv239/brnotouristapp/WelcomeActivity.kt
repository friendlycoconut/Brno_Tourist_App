package com.pv239.brnotouristapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.pv239.brnotouristapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var fullscreenContent: TextView
    private var isFullscreen: Boolean = false
    private val SPLASH_DELAY: Long = 3000L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isFullscreen = true

        supportActionBar?.hide()
        fullscreenContent = binding.fullscreenContent

        Handler().postDelayed({
            if (isNetworkAvailable()) {
                // If online, start the content download
            } else {
                // If offline, show an error message
                showToastMessage(getString(R.string.offline_mode))
            }
            binding.status.visibility = View.INVISIBLE

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = baseContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork ?: return false
        val nc = cm.getNetworkCapabilities(activeNetwork) ?: return false
        return nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI)
    }

}