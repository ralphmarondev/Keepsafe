package com.ralphmarondev.keepsafe.core.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ralphmarondev.keepsafe.core.data.local.database.Converters
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Entity(tableName = "families")
@TypeConverters(Converters::class)
data class FamilyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
    val isDeleted: Boolean = false
)
