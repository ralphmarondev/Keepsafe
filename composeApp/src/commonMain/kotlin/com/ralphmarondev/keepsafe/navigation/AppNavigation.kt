package com.ralphmarondev.keepsafe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.keepsafe.features.family.presentation.family_list.FamilyListScreenRoot

@Composable
fun AppNavigation(
    startDestination: Routes = Routes.FamilyList,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.FamilyList> {
            FamilyListScreenRoot()
        }
    }
}