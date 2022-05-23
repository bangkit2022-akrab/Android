package com.C22PS320.Akrab.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val NAME = stringPreferencesKey("name")
    private val EMAIL= stringPreferencesKey("email")
    private val TOKEN = stringPreferencesKey("token")

    fun getUserToken(): Flow<String?> {
        return dataStore.data.map {
            it[TOKEN]
        }
    }
    fun getUserName(): Flow<String?> {
        return dataStore.data.map {
            it[NAME]
        }
    }
    suspend fun SaveUserSession(name:String, email: String,token: String) {
        dataStore.edit {
            it[NAME] = name
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