package com.ralphmarondev.keepsafe.features.family.presentation.family_list

import com.ralphmarondev.keepsafe.core.domain.model.Member

data class FamilyListState(
    val navigateBack: Boolean = false,
    val members: List<Member> = emptyList(),
    val isError: Boolean = false,
    val showMessage: Boolean = false,
    val message: String? = null,
    val isRefreshing: Boolean = false
)