package com.ralphmarondev.keepsafe.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.domain.model.FamilyRole
import com.ralphmarondev.keepsafe.core.domain.model.Gender
import com.ralphmarondev.keepsafe.core.domain.model.Member
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.FamilyIdChange -> familyIdChange(action.id)
            is RegisterAction.FamilyNameChange -> familyNameChange(action.name)
            is RegisterAction.EmailChange -> emailChange(action.email)
            is RegisterAction.PasswordChange -> passwordChange(action.password)
            is RegisterAction.FirstNameChange -> firstNameChange(action.firstName)
            is RegisterAction.LastNameChange -> lastNameChange(action.lastName)
            is RegisterAction.MaidenNameChange -> maidenNameChange(action.maidenName)
            is RegisterAction.MiddleNameChange -> middleNameChange(action.middleName)
            is RegisterAction.BirthdayChange -> birthdayChange(action.birthday)
            is RegisterAction.GenderChange -> genderChange(action.gender)
            is RegisterAction.RoleChange -> roleChange(action.role)
            is RegisterAction.ScreenChange -> screenChange(action.screen)
            RegisterAction.Register -> register()
            RegisterAction.Login -> login()
        }
    }

    private fun familyIdChange(id: String) {
        _state.update { it.copy(familyId = id) }
    }

    private fun familyNameChange(familyName: String) {
        _state.update { it.copy(familyName = familyName) }
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

    private fun login() {
        _state.update { it.copy(navigateToLogin = true) }
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

                val member = Member(

                )
                when (val result = repository.register(member)) {
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
                        _state.update { it.copy(isRegistered = true) }
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