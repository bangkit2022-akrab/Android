package com.C22PS320.Akrab.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val NAME = stringPreferencesKey("name")
    private val EMAIL= stringPreferencesKey("email")
    private val TOKEN = stringPreferencesKey("token")
    suspend fun SaveUserSession(email: String,token: String) {
        dataStore.edit {
            it[EMAIL] = email
            it[TOKEN] = token
        }
    }
    suspend fun deleteAllData(){
        dataStore.edit {
            it.clear()
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}