package com.ralphmarondev.keepsafe.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.FamilyIdChange -> familyIdChange(action.id)
            is LoginAction.EmailChange -> emailChange(action.email)
            is LoginAction.PasswordChange -> passwordChange(action.password)
            LoginAction.Login -> login()
            LoginAction.Register -> register()
        }
    }

    private fun familyIdChange(id: Long) {
        _state.update { it.copy(familyId = id) }
    }

    private fun emailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    private fun passwordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    private fun login() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoggingIn = true,
                        isLoggedIn = false,
                        isError = false,
                        showMessage = false,
                        message = null
                    )
                }
                val email = _state.value.email.trim()
                val password = _state.value.password.trim()
                val familyId = _state.value.familyId

                val result = repository.login(
                    email = email,
                    password = password,
                    familyId = familyId
                )

                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoggedIn = true,
                                showMessage = true,
                                message = "Login successful!"
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isError = true,
                                showMessage = true,
                                message = result.message
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
                _state.update { it.copy(isLoggingIn = false) }
            }
        }
    }

    private fun register() {
        _state.update { it.copy(navigateToRegister = true) }
    }
}