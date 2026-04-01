package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.core.data.local.database.DatabaseFactory
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val platformModule: Module = module {
    single {
        AppPreferences.create {
            val userHome = System.getProperty("user.home")
            val prefsDir = File(userHome, ".keepsafe")
            prefsDir.mkdirs()
            File(prefsDir, AppPreferences.DATASTORE_FILENAME).absolutePath
        }
    }
    single { DatabaseFactory() }
}