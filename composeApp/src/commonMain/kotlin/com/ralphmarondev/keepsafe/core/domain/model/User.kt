package com.ralphmarondev.keepsafe.core.domain.model

data class User(
    val id: Long = 0,
    val username: String = "",
    val password: String = "", // only used on login
    val fullName: String = ""
)