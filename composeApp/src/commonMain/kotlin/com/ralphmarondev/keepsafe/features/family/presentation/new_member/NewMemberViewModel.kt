package com.ralphmarondev.keepsafe.features.family.presentation.new_member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.domain.model.FamilyRole
import com.ralphmarondev.keepsafe.core.domain.model.Gender
import com.ralphmarondev.keepsafe.core.domain.model.Member
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.features.family.domain.repository.FamilyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewMemberViewModel(
    private val familyRepository: FamilyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NewMemberState())
    val state = _state.asStateFlow()

    fun onAction(action: NewMemberAction) {
        when (action) {
            is NewMemberAction.FirstNameChange -> firstNameChange(action.firstName)
            is NewMemberAction.LastNameChange -> lastNameChange(action.lastName)
            is NewMemberAction.MiddleNameChange -> middleNameChange(action.middleName)
            is NewMemberAction.MaidenNameChange -> maidenNameChange(action.maidenName)
            is NewMemberAction.BirthdayChange -> birthdayChange(action.birthday)
            is NewMemberAction.RoleChange -> roleChange(action.role)
            is NewMemberAction.GenderChange -> genderChange(action.gender)
            is NewMemberAction.EmailChange -> emailChange(action.email)
            is NewMemberAction.PasswordChange -> passwordChange(action.password)
            is NewMemberAction.ScreenChange -> screenChange(action.screen)
            NewMemberAction.ClearNavigation -> clearNavigation()
            NewMemberAction.NavigateBack -> navigateBack()
            NewMemberAction.Register -> register()
        }
    }

    private fun emailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    private fun passwordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    private fun firstNameChange(firstName: String) {
        _state.update { it.copy(firstName = firstName) }
    }

    private fun lastNameChange(lastName: String) {
        _state.update { it.copy(lastName = lastName) }
    }

    private fun middleNameChange(middleName: String) {
        _state.update { it.copy(middleName = middleName) }
    }

    private fun birthdayChange(birthday: String) {
        _state.update { it.copy(birthday = birthday) }
    }

    private fun maidenNameChange(maidenName: String) {
        _state.update { it.copy(maidenName = maidenName) }
    }

    private fun genderChange(gender: Gender) {
        _state.update { it.copy(gender = gender) }
    }

    private fun roleChange(role: FamilyRole) {
        _state.update { it.copy(role = role) }
    }

    private fun screenChange(screen: Screen) {
        _state.update { it.copy(currentScreen = screen) }
    }

    private fun clearNavigation() {
        _state.update { it.copy(navigateBack = false) }
    }

    private fun navigateBack() {
        _state.update { it.copy(navigateBack = true) }
    }

    private fun register() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isRegistering = true,
                        isRegistered = false,
                        isError = false,
                        showMessage = false,
                        message = null
                    )
                }

                val current = state.value
                val member = Member(
                    email = current.email,
                    password = current.password,
                    firstName = current.firstName,
                    lastName = current.lastName,
                    middleName = current.middleName,
                    maidenName = current.maidenName,
                    role = current.role,
                    gender = current.gender,
                    birthday = current.birthday,
                    isAdmin = true
                )
                val result = familyRepository.addNewMember(
                    member = member
                )
                when (result) {
                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isError = true,
                                showMessage = true,
                                message = result.message
                            )
                        }
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isRegistered = true,
                                message = "Member registered successfully.",
                                showMessage = true,
                                currentScreen = Screen.PersonalInformation,
                                email = "",
                                password = "",
                                firstName = "",
                                lastName = "",
                                middleName = "",
                                maidenName = "",
                                gender = Gender.Male,
                                role = FamilyRole.Guardian,
                                birthday = "",
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isError = true,
                        showMessage = true,
                        message = e.message
                    )
                }
            } finally {
                _state.update { it.copy(isRegistering = false) }
            }
        }
    }
}