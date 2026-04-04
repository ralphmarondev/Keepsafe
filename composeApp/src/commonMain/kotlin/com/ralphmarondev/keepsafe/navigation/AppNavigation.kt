package com.ralphmarondev.keepsafe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.keepsafe.features.auth.presentation.login.LoginScreenRoot
import com.ralphmarondev.keepsafe.features.auth.presentation.register.RegisterScreenRoot
import com.ralphmarondev.keepsafe.features.family.presentation.member_list.FamilyListScreenRoot

@Composable
fun AppNavigation(
    startDestination: Routes = Routes.Login,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.Login> {
            LoginScreenRoot(
                onLoginSuccessful = {
                    navController.navigate(Routes.MemberList) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onRegisterClick = {
                    navController.navigate(Routes.Register) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.Register> {
            RegisterScreenRoot(
                onRegistrationSuccessful = {
                    navController.navigate(Routes.MemberList) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onLoginClick = {
                    navController.navigate(Routes.Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.MemberList> {
            FamilyListScreenRoot()
        }
    }
}