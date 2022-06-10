package com.C22PS320.Akrab.ui.kelas

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.C22PS320.Akrab.adapter.LevelAdapter
import com.C22PS320.Akrab.databinding.ActivityClassBinding
import com.C22PS320.Akrab.network.response.LevelResponse
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory
import com.C22PS320.Akrab.ui.modulquiz.ModulQuizActivity
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            val token = classViewModel.getToken()
            classViewModel.getLevelClass(kelas, token)
        }
        classViewModel.levelClass.observe(this) {
            setReviewData(it)
        }
        classViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
//        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//        binding.rvUsers.addItemDecoration(itemDecoration)
    }
    private fun setReviewData(UsersData: LevelResponse) {
        val adapter = LevelAdapter(UsersData.data)
        binding.rvUsers.adapter = adapter
        adapter.setOnItemClickCallback(object : LevelAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
               goToModule(data)
            }
        })
    }
    private fun goToModule(data: String) {
        val i = Intent(this, ModulQuizActivity::class.java)
        i.putExtra("level",data)
        startActivity(i)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private var KELAS = "class"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}