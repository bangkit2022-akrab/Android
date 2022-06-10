package com.C22PS320.Akrab.ui.main

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.C22PS320.Akrab.network.ApiConfig
import com.C22PS320.Akrab.network.response.LevelResponse
import com.C22PS320.Akrab.network.response.ModuleResponse
import com.C22PS320.Akrab.preferences.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences): ViewModel() {
    private val _module = MutableLiveData<ModuleResponse>()
    val module: LiveData<ModuleResponse> = _module

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getToken(): String? {
        return pref.getUserToken()
    }
    suspend fun getName(): String? {
        return pref.getName()
    }
    fun getUserName(): LiveData<String?> {
        return pref.getUserName().asLiveData()
    }
    fun getUserData(): LiveData<String?> {
        return pref.getEmail().asLiveData()
    }
    fun deleteAllDatas() {
        viewModelScope.launch {
            pref.deleteAllData()
        }
    }
    fun getModule(token: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getModule(token.toString())
        client.enqueue(object : Callback<ModuleResponse> {
            override fun onResponse(
                call: Call<ModuleResponse>,
                response: Response<ModuleResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _module.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ModuleResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}