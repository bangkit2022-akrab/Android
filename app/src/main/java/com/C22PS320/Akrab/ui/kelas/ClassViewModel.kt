package com.C22PS320.Akrab.ui.kelas

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.C22PS320.Akrab.network.ApiConfig
import com.C22PS320.Akrab.network.response.LevelResponse
import com.C22PS320.Akrab.preferences.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassViewModel(private val pref: SettingPreferences): ViewModel() {
    private val _levelClass = MutableLiveData<LevelResponse>()
    val levelClass: LiveData<LevelResponse> = _levelClass

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getToken(): String? {
        return pref.getUserToken()
    }
    fun getLevelClass(kelas:String?, token: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getLevel(kelas.toString(),token.toString())
        client.enqueue(object : Callback<LevelResponse> {
            override fun onResponse(
                call: Call<LevelResponse>,
                response: Response<LevelResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _levelClass.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LevelResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}