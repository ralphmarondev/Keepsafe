package com.ralphmarondev.keepsafe.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ralphmarondev.keepsafe.core.data.local.database.AppDatabase
import com.ralphmarondev.keepsafe.core.data.local.database.DatabaseFactory
import org.koin.dsl.module

val coreModule = module {
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<AppDatabase>().userDao }
}