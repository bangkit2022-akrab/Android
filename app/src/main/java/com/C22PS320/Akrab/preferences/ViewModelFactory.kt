package com.C22PS320.Akrab.preferences

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.C22PS320.Akrab.ui.login.LoginViewModel

class ViewModelFactory(private val pref: SettingPreferences,private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
//            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
//                MainViewModel(pref, Injection.provideRepository(context)) as T
//            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}