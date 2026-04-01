package com.ralphmarondev.keepsafe.features.auth.presentation.register

import com.ralphmarondev.keepsafe.core.domain.model.FamilyRole
import com.ralphmarondev.keepsafe.core.domain.model.Gender

enum class Screen {
    FamilyProfile,
    PersonalInformation,
    AccountInformation,
    OptionalInformation,
    Summary
}

data class RegisterState(
    val isRegistered: Boolean = false,
    val isRegistering: Boolean = false,
    val isError: Boolean = false,
    val showMessage: Boolean = false,
    val message: String? = null,
    val navigateToLogin: Boolean = false,
    val familyId: String = "",
    val familyName: String = "",
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val maidenName: String = "",
    val gender: Gender = Gender.Male,
    val role: FamilyRole = FamilyRole.Guardian,
    val birthday: String = "",
    val currentScreen: Screen = Screen.FamilyProfile
)