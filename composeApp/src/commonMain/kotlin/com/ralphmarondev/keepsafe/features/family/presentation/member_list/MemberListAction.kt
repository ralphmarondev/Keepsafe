package com.ralphmarondev.keepsafe.features.family.presentation.member_list

sealed interface MemberListAction {
    data object AddNewMember : MemberListAction
}