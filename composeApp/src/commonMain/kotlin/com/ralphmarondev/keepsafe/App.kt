package com.ralphmarondev.keepsafe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.presentation.theme.KeepsafeTheme
import com.ralphmarondev.keepsafe.core.presentation.theme.LocalThemeState
import com.ralphmarondev.keepsafe.core.presentation.theme.ThemeProvider
import com.ralphmarondev.keepsafe.navigation.AppNavigation
import com.ralphmarondev.keepsafe.navigation.Routes
import kotlinx.coroutines.flow.first

@Composable
fun App(preferences: AppPreferences) {
    ThemeProvider(preferences) {
        val themeState = LocalThemeState.current
        KeepsafeTheme(darkTheme = themeState.darkTheme.value) {
            var startDestination by remember { mutableStateOf<Routes?>(null) }

            LaunchedEffect(Unit) {
                startDestination = when (preferences.isAuthenticated.first()) {
                    true -> Routes.MemberList
                    false -> Routes.Login
                }
            }

            startDestination?.let { destination ->
                AppNavigation(startDestination = destination)
            }
        }
    }
}