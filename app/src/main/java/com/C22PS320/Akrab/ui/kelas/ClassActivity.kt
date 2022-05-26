package com.C22PS320.Akrab.ui.kelas

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.C22PS320.Akrab.adapter.LevelAdapter
import com.C22PS320.Akrab.databinding.ActivityClassBinding
import com.C22PS320.Akrab.network.response.LevelResponse
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory

class ClassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityClassBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val kelas = intent.getStringExtra(KELAS)
        supportActionBar?.title = "KELAS ${kelas?.uppercase()}"
        val pref = SettingPreferences.getInstance(dataStore)
        val classViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            ClassViewModel::class.java
        )
        classViewModel.getToken().observe(this) {
            classViewModel.getUsersData(kelas, it)
        }
        classViewModel.users.observe(this) {
            setReviewData(it)
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
    }
    private fun setReviewData(UsersData: LevelResponse) {
        val adapter = LevelAdapter(UsersData.data)
        binding.rvUsers.adapter = adapter
//        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: UsersResponseItem) {
//                data.login?.let { showSelectedHero(it) }
//            }
//        })
    }
    companion object {
        private var KELAS = "class"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}