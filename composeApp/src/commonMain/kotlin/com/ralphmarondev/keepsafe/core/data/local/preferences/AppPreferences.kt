package com.ralphmarondev.keepsafe.core.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
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
        val LOCAL_FAMILY_ID = longPreferencesKey("local_family_id")
        val FIREBASE_FAMILY_ID = stringPreferencesKey("firebase_family_id")
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

    suspend fun setLocalFamilyId(localFamilyId: Long) {
        dataStore.edit { it[LOCAL_FAMILY_ID] = localFamilyId }
    }

    val currentLocalFamilyId: Flow<Long> = dataStore.data.map { it[LOCAL_FAMILY_ID] ?: 0 }

    suspend fun setFirebaseFamilyId(firebaseFamilyId: String) {
        dataStore.edit { it[FIREBASE_FAMILY_ID] = firebaseFamilyId }
    }

    val currentFirebaseFamilyId: Flow<String> = dataStore.data.map { it[FIREBASE_FAMILY_ID] ?: "" }

    suspend fun setAuthenticated(authenticated: Boolean) {
        dataStore.edit { it[AUTHENTICATED] = authenticated }
    }

    val isAuthenticated: Flow<Boolean> = dataStore.data.map { it[AUTHENTICATED] ?: false }
}