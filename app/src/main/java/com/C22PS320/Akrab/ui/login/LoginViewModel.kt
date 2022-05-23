package com.C22PS320.Akrab.ui.login

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.C22PS320.Akrab.custom.Event
import com.C22PS320.Akrab.network.ApiConfig
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.response.AuthResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: SettingPreferences): ViewModel() {
    private val _login = MutableLiveData<AuthResponse>()
    val login: LiveData<AuthResponse> = _login

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveUserSession(email: String, token: String) {
        viewModelScope.launch {
            pref.SaveUserSession(email, token)
        }
    }
    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginUser(email, password)
        client.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>
            ) {
                _isLoading.value =false
                if (response.isSuccessful && response.body() != null) {
                    _login.value = response.body()
                    _snackbarText.value = Event(response.body()?.meta?.message.toString())
                } else {
                    _snackbarText.value = Event(response.message())
                    Log.e(ContentValues.TAG, "onFailure but response: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}