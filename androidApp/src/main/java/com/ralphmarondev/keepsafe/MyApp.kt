package com.ralphmarondev.keepsafe

import android.app.Application
import com.ralphmarondev.keepsafe.di.initKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        Firebase.initialize(this@MyApp)
        initKoin {
            androidContext(this@MyApp)
        }
    }
}