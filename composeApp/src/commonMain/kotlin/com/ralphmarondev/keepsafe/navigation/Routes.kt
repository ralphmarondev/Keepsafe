package com.ralphmarondev.keepsafe.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object Login : Routes

    @Serializable
    data object Register : Routes

    @Serializable
    data object MemberList : Routes

    @Serializable
    data object NewMember : Routes
}