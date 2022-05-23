package com.C22PS320.Akrab.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.C22PS320.Akrab.preferences.SettingPreferences

class SplashScreenViewModel(private val pref: SettingPreferences): ViewModel() {
    fun getToken(): LiveData<String?> {
        return pref.getUserToken().asLiveData()
    }
}