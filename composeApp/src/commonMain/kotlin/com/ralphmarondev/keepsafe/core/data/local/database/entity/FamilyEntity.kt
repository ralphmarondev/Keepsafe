package com.ralphmarondev.keepsafe.core.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ralphmarondev.keepsafe.core.data.local.database.Converters

@Entity(tableName = "families")
@TypeConverters(Converters::class)
data class FamilyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val familyId: String = "",
    val familyName: String = "",
    val createdAt: Long = System.currentTimeMillis(), // Clock.System.now(),
    val updatedAt: Long = System.currentTimeMillis(), // Clock.System.now(),
    val isDeleted: Boolean = false
)
