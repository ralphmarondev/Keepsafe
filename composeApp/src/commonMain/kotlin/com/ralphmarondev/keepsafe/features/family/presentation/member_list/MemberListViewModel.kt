package com.ralphmarondev.keepsafe.features.family.presentation.member_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MemberListViewModel : ViewModel() {

    private val _state = MutableStateFlow(MemberListState())
    val state = _state.asStateFlow()

    fun onAction(action: MemberListAction) {
        when (action) {
            MemberListAction.AddNewMember -> addNewMember()
            MemberListAction.ClearNavigation -> clearNavigation()
        }
    }

    private fun addNewMember() {
        _state.update { it.copy(navigateToNewMember = true) }
    }

    private fun clearNavigation() {
        _state.update { it.copy(navigateToNewMember = false) }
    }
}