package com.ralphmarondev.keepsafe.core.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Family(
    val id: Long = 0, // local database
    val familyId: String = "", // firebase
    val familyName: String = "",
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
    val isDeleted: Boolean = false
)