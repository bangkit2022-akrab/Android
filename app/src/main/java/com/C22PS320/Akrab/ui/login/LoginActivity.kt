package com.C22PS320.Akrab.ui.login

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
import com.C22PS320.Akrab.databinding.ActivityLoginBinding
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory
import com.C22PS320.Akrab.ui.main.MainActivity
import com.C22PS320.Akrab.ui.register.RegisterActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            LoginViewModel::class.java
        )

        loginViewModel.login.observe(this) {
            if (it.meta?.message == "Success") {
                it.data?.let { it1 -> loginViewModel.saveUserSession(it1.user?.email.toString(), it1.token.toString()) }
                userLogin()
            }
        }
        loginViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        binding.Loginbutton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val pass = binding.loginPassword.text.toString()
            loginViewModel.loginUser(email,pass)
        }
        binding.register.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
        setMyButtonEnable()

        binding.loginEmail.addTextChangedListener {
            setMyButtonEnable()
        }
        binding.loginPassword.addTextChangedListener {
            setMyButtonEnable()
        }
    }
    private fun setMyButtonEnable() {
        val usern = binding.loginEmail.text
        val pass = binding.loginPassword.text
        binding.Loginbutton.isEnabled = usern != null && usern.toString().isNotEmpty() && pass != null && pass.toString().isNotEmpty()
    }
    private fun userLogin() {
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