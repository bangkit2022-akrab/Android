package com.C22PS320.Akrab.ui.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.C22PS320.Akrab.databinding.ActivityRegisterBinding
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory
import com.C22PS320.Akrab.ui.login.LoginActivity
import com.C22PS320.Akrab.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        val registerViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            RegisterViewModel::class.java
        )

        registerViewModel.regist.observe(this) {
            if (it.meta?.message == "Success") {
                it.data?.let { it1 -> registerViewModel.saveUserSession(
                    it1.user?.fullName.toString(),
                    it1.user?.email.toString(),
                    it1.token.toString()) }
                userRegister()
            }
        }
        registerViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        binding.registbutton.setOnClickListener {
            val usern = binding.registUsername.text.toString()
            val email = binding.registEmail.text.toString()
            val pass = binding.registPassword.text.toString()
            registerViewModel.registerUser(usern,email,pass)
        }

        binding.register.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
        setMyButtonEnable()

        binding.registUsername.addTextChangedListener {
            setMyButtonEnable()
        }
        binding.registEmail.addTextChangedListener {
            setMyButtonEnable()
        }
        binding.registPassword.addTextChangedListener {
            setMyButtonEnable()
        }
    }
    private fun setMyButtonEnable() {
        val usern = binding.registUsername.text
        val email = binding.registEmail.text
        val pass = binding.registPassword.text
        binding.registbutton.isEnabled = usern != null && usern.toString().isNotEmpty() &&  email != null && email.toString().isNotEmpty() && pass != null && pass.toString().isNotEmpty()
    }
    private fun userRegister() {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },2500)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}