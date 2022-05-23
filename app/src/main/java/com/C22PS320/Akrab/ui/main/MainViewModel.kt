package com.C22PS320.Akrab.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.C22PS320.Akrab.preferences.SettingPreferences

class MainViewModel(private val pref: SettingPreferences): ViewModel() {
    fun getToken(): LiveData<String?> {
        return pref.getUserToken().asLiveData()
    }
    fun getName(): LiveData<String?> {
        return pref.getUserName().asLiveData()
    }
}