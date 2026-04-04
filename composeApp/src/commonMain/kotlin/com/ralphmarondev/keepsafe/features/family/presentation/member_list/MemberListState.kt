package com.ralphmarondev.keepsafe.features.family.presentation.member_list

import com.ralphmarondev.keepsafe.core.domain.model.Member

data class MemberListState(
    val navigateBack: Boolean = false,
    val navigateToNewMember: Boolean = false,
    val members: List<Member> = emptyList(),
    val isError: Boolean = false,
    val showMessage: Boolean = false,
    val message: String? = null,
    val isRefreshing: Boolean = false
)