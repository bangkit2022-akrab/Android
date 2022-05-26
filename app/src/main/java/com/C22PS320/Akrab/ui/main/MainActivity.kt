package com.C22PS320.Akrab.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.C22PS320.Akrab.databinding.ActivityMainBinding
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory
import com.C22PS320.Akrab.ui.kelas.ClassActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            MainViewModel::class.java
        )
        mainViewModel.getName().observe(this) {
            binding.tvUsername.text = it
        }
        binding.btnKelashuruf.setOnClickListener {
            val i = Intent(this, ClassActivity::class.java)
            i.putExtra("class","huruf")
            startActivity(i)
        }
        binding.btnKelasangka.setOnClickListener {
            val i = Intent(this, ClassActivity::class.java)
            i.putExtra("class","angka")
            startActivity(i)
        }

    }
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}