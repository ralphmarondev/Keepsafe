package com.ralphmarondev.keepsafe.core.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

class AppPreferences(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val EMAIL = stringPreferencesKey("email")
        val FAMILY = stringPreferencesKey("family")
        val AUTHENTICATED = booleanPreferencesKey("authenticated")
        const val DATASTORE_FILENAME = "keepsafe.preferences_pb"

        fun create(producePath: () -> String): AppPreferences {
            val dataStore = PreferenceDataStoreFactory.createWithPath(
                produceFile = { producePath().toPath() }
            )
            return AppPreferences(dataStore)
        }
    }

    suspend fun setDarkMode(value: Boolean) {
        dataStore.edit { it[DARK_MODE] = value }
    }

    val isDarkMode: Flow<Boolean> = dataStore.data.map { it[DARK_MODE] == true }

    suspend fun setCurrentUser(email: String) {
        dataStore.edit { it[EMAIL] = email }
    }

    val currentUser: Flow<String> = dataStore.data.map { it[EMAIL] ?: "" }

    suspend fun setCurrentFamily(familyId: String) {
        dataStore.edit { it[FAMILY] = familyId }
    }

    val currentFamily: Flow<String> = dataStore.data.map { it[FAMILY] ?: "" }

    suspend fun setAuthenticated(authenticated: Boolean) {
        dataStore.edit { it[AUTHENTICATED] = authenticated }
    }

    val isAuthenticated: Flow<Boolean> = dataStore.data.map { it[AUTHENTICATED] ?: false }
}