package com.ralphmarondev.keepsafe.core.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ralphmarondev.keepsafe.core.data.local.database.Converters
import com.ralphmarondev.keepsafe.core.domain.model.FamilyRole
import com.ralphmarondev.keepsafe.core.domain.model.Gender

@Entity(tableName = "members")
@TypeConverters(Converters::class)
data class MemberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val localFamilyId: Long = 0,
    val familyId: String = "",
    val email: String = "",
    val password: String = "",
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val maidenName: String? = null,
    val gender: Gender? = null,
    val role: FamilyRole? = null,
    val childOrder: Int? = null,
    val birthday: String? = null,
    val isAdmin: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(), // Clock.System.now(),
    val updatedAt: Long = System.currentTimeMillis(),// Clock.System.now(),
    val isDeleted: Boolean = false
)