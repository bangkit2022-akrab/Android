package com.C22PS320.Akrab.ui.modulquiz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.C22PS320.Akrab.R
import com.C22PS320.Akrab.databinding.ActivityModulQuizBinding
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory

class ModulQuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModulQuizBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityModulQuizBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        val level = intent.getStringExtra(level)
        val pref = SettingPreferences.getInstance(dataStore)
        val modulQuizViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            ModulQuizViewModel::class.java
        )
        modulQuizViewModel.getToken().observe(this) {
            modulQuizViewModel.getModuleQuiz(level, it)
        }
        modulQuizViewModel.moduleQuiz.observe(this) {
            val mFragmentManager = supportFragmentManager
            val mHomeFragment = ModuleLearnFragment.newInstance()
            val mBundle = Bundle()
            mBundle.putParcelable(ModuleLearnFragment.DATAMODULEQUIZ, it)
            mBundle.putInt(ModuleLearnFragment.PIVOT, INIT)
            it.data?.modul?.let { it1 -> mBundle.putInt(ModuleLearnFragment.MAX, it1?.size) }
            mHomeFragment.arguments = mBundle
            val fragment = mFragmentManager.findFragmentByTag(ModuleLearnFragment::class.java.simpleName)
            if (fragment !is ModuleLearnFragment) {
                mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, mHomeFragment, ModuleLearnFragment::class.java.simpleName)
                    .commit()
            }
        }
        modulQuizViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    companion object{
        private var level = "level"
        private val INIT = 0
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}