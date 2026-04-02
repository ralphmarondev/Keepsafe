package com.ralphmarondev.keepsafe.core.domain.model

data class Family(
    val id: Long = 0, // local database
    val familyId: String = "", // firebase
    val familyName: String = "",
    val createdAt: Long = System.currentTimeMillis(),// Clock.System.now(),
    val updatedAt: Long = System.currentTimeMillis(),//Clock.System.now(),
    val isDeleted: Boolean = false
)