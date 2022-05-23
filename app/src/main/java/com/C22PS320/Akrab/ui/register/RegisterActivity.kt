package com.C22PS320.Akrab.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.C22PS320.Akrab.databinding.ActivityRegisterBinding
import com.C22PS320.Akrab.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

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
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}