package com.ralphmarondev.keepsafe.features.family.presentation.new_member

import com.ralphmarondev.keepsafe.core.domain.model.FamilyRole
import com.ralphmarondev.keepsafe.core.domain.model.Gender

enum class Screen {
    PersonalInformation,
    AccountInformation,
    OptionalInformation,
    Summary
}

data class NewMemberState(
    val navigateBack: Boolean = false,
    val isRegistered: Boolean = false,
    val isRegistering: Boolean = false,
    val isError: Boolean = false,
    val showMessage: Boolean = false,
    val message: String? = null,
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val maidenName: String = "",
    val gender: Gender = Gender.Male,
    val role: FamilyRole = FamilyRole.Guardian,
    val birthday: String = "",
    val currentScreen: Screen = Screen.PersonalInformation
)