package com.ralphmarondev.keepsafe.features.family.presentation.member_list

sealed interface MemberListAction {
    data object AddNewMember : MemberListAction
    data object ClearNavigation : MemberListAction
    data object Refresh : MemberListAction
}