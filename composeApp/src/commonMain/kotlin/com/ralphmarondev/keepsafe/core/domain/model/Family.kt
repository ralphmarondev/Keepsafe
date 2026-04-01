package com.ralphmarondev.keepsafe.core.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Family(
    val id: Long = 0,
    val name: String = "",
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
    val isDeleted: Boolean = false
)