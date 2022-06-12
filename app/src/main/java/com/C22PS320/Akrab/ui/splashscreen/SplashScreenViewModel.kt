package com.C22PS320.Akrab.ui.splashscreen

import androidx.lifecycle.ViewModel
import com.C22PS320.Akrab.preferences.SettingPreferences

class SplashScreenViewModel(private val pref: SettingPreferences): ViewModel() {
    suspend fun getToken(): String? {
        return pref.getUserToken()
    }
}