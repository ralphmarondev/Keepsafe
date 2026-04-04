package com.ralphmarondev.keepsafe.features.family.presentation.member_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MemberListViewModel : ViewModel() {

    private val _state = MutableStateFlow(MemberListState())
    val state = _state.asStateFlow()

    fun onAction(action: MemberListAction) {

    }
}