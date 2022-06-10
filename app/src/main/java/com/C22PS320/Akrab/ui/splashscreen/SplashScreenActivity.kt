package com.C22PS320.Akrab.ui.splashscreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.C22PS320.Akrab.databinding.ActivitySplashScreenBinding
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory
import com.C22PS320.Akrab.ui.login.LoginActivity
import com.C22PS320.Akrab.ui.main.MainActivity
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        val pref = SettingPreferences.getInstance(dataStore)
        val splashScreenViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            SplashScreenViewModel::class.java
        )
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            val token = splashScreenViewModel.getToken()
            binding.progressBar.visibility = View.GONE
            if (token.isNullOrEmpty()){
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            }else {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            }
            finish()
        }
    }
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}