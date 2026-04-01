package com.ralphmarondev.keepsafe.features.auth.presentation.register

import com.ralphmarondev.keepsafe.core.domain.model.FamilyRole
import com.ralphmarondev.keepsafe.core.domain.model.Gender

sealed interface RegisterAction {
    data object Login : RegisterAction
    data object Register : RegisterAction
    data class FamilyIdChange(val id: String) : RegisterAction
    data class FamilyNameChange(val name: String) : RegisterAction
    data class EmailChange(val email: String) : RegisterAction
    data class PasswordChange(val password: String) : RegisterAction
    data class FirstNameChange(val firstName: String) : RegisterAction
    data class LastNameChange(val lastName: String) : RegisterAction
    data class MiddleNameChange(val middleName: String) : RegisterAction
    data class MaidenNameChange(val maidenName: String) : RegisterAction
    data class GenderChange(val gender: Gender) : RegisterAction
    data class RoleChange(val role: FamilyRole) : RegisterAction
    data class BirthdayChange(val birthday: String) : RegisterAction
}