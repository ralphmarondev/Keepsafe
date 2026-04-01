package com.ralphmarondev.keepsafe.di

import android.content.Context
import com.ralphmarondev.keepsafe.core.data.local.database.DatabaseFactory
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        AppPreferences.create {
            get<Context>().filesDir.resolve(
                relative = AppPreferences.DATASTORE_FILENAME
            ).absolutePath
        }
    }
    single { DatabaseFactory(androidApplication()) }
}