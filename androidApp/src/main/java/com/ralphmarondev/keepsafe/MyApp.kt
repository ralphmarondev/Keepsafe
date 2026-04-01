package com.ralphmarondev.keepsafe

import android.app.Application
import com.ralphmarondev.keepsafe.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MyApp)
        }
    }
}