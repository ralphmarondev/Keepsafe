package com.ralphmarondev.keepsafe.features.family.presentation.new_member

import com.ralphmarondev.keepsafe.core.domain.model.FamilyRole
import com.ralphmarondev.keepsafe.core.domain.model.Gender

sealed interface NewMemberAction {
    data object NavigateBack : NewMemberAction
    data object ClearNavigation : NewMemberAction
    data object Register : NewMemberAction
    data class EmailChange(val email: String) : NewMemberAction
    data class PasswordChange(val password: String) : NewMemberAction
    data class FirstNameChange(val firstName: String) : NewMemberAction
    data class LastNameChange(val lastName: String) : NewMemberAction
    data class MiddleNameChange(val middleName: String) : NewMemberAction
    data class MaidenNameChange(val maidenName: String) : NewMemberAction
    data class GenderChange(val gender: Gender) : NewMemberAction
    data class RoleChange(val role: FamilyRole) : NewMemberAction
    data class BirthdayChange(val birthday: String) : NewMemberAction
    data class ScreenChange(val screen: Screen) : NewMemberAction
}