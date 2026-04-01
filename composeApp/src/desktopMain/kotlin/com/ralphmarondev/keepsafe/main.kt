package com.ralphmarondev.keepsafe

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.di.initKoin
import org.koin.compose.koinInject

fun main() {
    initKoin()
    application {
        val preferences = koinInject<AppPreferences>()

        Window(
            onCloseRequest = ::exitApplication,
            title = "Keepsafe",
        ) {
            App(preferences)
        }
    }
}