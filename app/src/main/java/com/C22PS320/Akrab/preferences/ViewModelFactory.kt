package com.C22PS320.Akrab.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.C22PS320.Akrab.ui.kelas.ClassViewModel
import com.C22PS320.Akrab.ui.login.LoginViewModel
import com.C22PS320.Akrab.ui.main.MainViewModel
import com.C22PS320.Akrab.ui.modulquiz.ModulQuizViewModel
import com.C22PS320.Akrab.ui.register.RegisterViewModel
import com.C22PS320.Akrab.ui.splashscreen.SplashScreenViewModel

class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ClassViewModel::class.java) -> {
                ClassViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ModulQuizViewModel::class.java) -> {
                ModulQuizViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}