package com.C22PS320.Akrab.ui.modulquiz

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.C22PS320.Akrab.network.ApiConfig
import com.C22PS320.Akrab.network.response.ModuleQuizResponse
import com.C22PS320.Akrab.preferences.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModulQuizViewModel(private val pref: SettingPreferences): ViewModel() {
    private val _moduleQuiz = MutableLiveData<ModuleQuizResponse>()
    val moduleQuiz: LiveData<ModuleQuizResponse> = _moduleQuiz

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getToken(): String? {
        return pref.getUserToken()
    }
    fun getModuleQuiz(level:String?, token: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getModuleQuiz(level.toString(),token.toString())
        client.enqueue(object : Callback<ModuleQuizResponse> {
            override fun onResponse(
                call: Call<ModuleQuizResponse>,
                response: Response<ModuleQuizResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _moduleQuiz.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ModuleQuizResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}