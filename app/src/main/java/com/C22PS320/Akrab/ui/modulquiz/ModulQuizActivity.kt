package com.C22PS320.Akrab.ui.modulquiz

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.C22PS320.Akrab.R
import com.C22PS320.Akrab.databinding.ActivityModulQuizBinding
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory
import com.C22PS320.Akrab.ui.modulquiz.learnmodule.ModuleLearnFragment
import com.C22PS320.Akrab.ui.modulquiz.learnquiz.QuizLearnFragment
import kotlinx.coroutines.launch

class ModulQuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModulQuizBinding

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityModulQuizBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        val level = intent.getStringExtra(level)
        val pref = SettingPreferences.getInstance(dataStore)
        val modulQuizViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            ModulQuizViewModel::class.java
        )

        val mFragmentManager = supportFragmentManager
        val frgm = mFragmentManager.findFragmentByTag(QuizLearnFragment::class.java.simpleName)

        if (frgm==null) {
            lifecycleScope.launch {
                val token = modulQuizViewModel.getToken()
                modulQuizViewModel.getModuleQuiz(level, token)
            }

            modulQuizViewModel.moduleQuiz.observe(this) {
                val mHomeFragment = ModuleLearnFragment.newInstance()
                val mBundle = Bundle()
                mBundle.putParcelable(ModuleLearnFragment.DATAMODULEQUIZ, it)
                mBundle.putInt(ModuleLearnFragment.PIVOT, INIT)
                it.data?.modul?.let { it1 -> mBundle.putInt(ModuleLearnFragment.MAX, it1.size) }
                mHomeFragment.arguments = mBundle
                    mFragmentManager
                        .beginTransaction()
                        .add(
                            R.id.frame_container,
                            mHomeFragment,
                            ModuleLearnFragment::class.java.simpleName
                        )
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
        private const val INIT = 0
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}

