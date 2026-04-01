package com.ralphmarondev.keepsafe.features.auth.presentation.login

sealed interface LoginAction {
    data object Login : LoginAction
    data object Register : LoginAction
    data class FamilyIdChange(val id: Long) : LoginAction
    data class EmailChange(val email: String) : LoginAction
    data class PasswordChange(val password: String) : LoginAction
}