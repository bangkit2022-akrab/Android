package com.C22PS320.Akrab.ui.splashscreen


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.C22PS320.Akrab.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.progressBar.visibility = View.VISIBLE
    }
}