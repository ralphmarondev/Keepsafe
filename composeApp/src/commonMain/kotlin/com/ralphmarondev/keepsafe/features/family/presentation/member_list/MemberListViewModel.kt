package com.ralphmarondev.keepsafe.features.family.presentation.member_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.features.family.domain.repository.FamilyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemberListViewModel(
    private val familyRepository: FamilyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MemberListState())
    val state = _state.asStateFlow()

    init {
        loadAllMembers()
    }

    fun onAction(action: MemberListAction) {
        when (action) {
            MemberListAction.AddNewMember -> addNewMember()
            MemberListAction.ClearNavigation -> clearNavigation()
            MemberListAction.Refresh -> loadAllMembers(isRefreshing = true)
        }
    }

    private fun addNewMember() {
        _state.update { it.copy(navigateToNewMember = true) }
    }

    private fun clearNavigation() {
        _state.update { it.copy(navigateToNewMember = false) }
    }

    private fun loadAllMembers(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isRefreshing = isRefreshing,
                        message = null,
                        isError = false,
                        showMessage = false
                    )
                }

                val members = familyRepository.getAllMembers()
                if (isRefreshing) {
                    delay(1500)
                }
                _state.update { it.copy(members = members) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        message = e.message,
                        isError = true,
                        showMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isRefreshing = false) }
            }
        }
    }
}