package com.ralphmarondev.keepsafe

import androidx.compose.runtime.Composable
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.presentation.theme.KeepsafeTheme
import com.ralphmarondev.keepsafe.core.presentation.theme.LocalThemeState
import com.ralphmarondev.keepsafe.core.presentation.theme.ThemeProvider
import com.ralphmarondev.keepsafe.navigation.AppNavigation

@Composable
fun App(preferences: AppPreferences) {
    ThemeProvider(preferences) {
        val themeState = LocalThemeState.current
        KeepsafeTheme(darkTheme = themeState.darkTheme.value) {
            AppNavigation()
        }
    }
}