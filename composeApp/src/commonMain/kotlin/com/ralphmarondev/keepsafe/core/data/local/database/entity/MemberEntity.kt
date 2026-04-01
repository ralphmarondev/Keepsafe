package com.ralphmarondev.keepsafe.core.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ralphmarondev.keepsafe.core.data.local.database.Converters
import com.ralphmarondev.keepsafe.core.domain.model.FamilyRole
import com.ralphmarondev.keepsafe.core.domain.model.Gender
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

@Entity(tableName = "members")
@TypeConverters(Converters::class)
data class MemberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String = "",
    val password: String = "",
    val familyId: Long = 0,
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val maidenName: String? = null,
    val gender: Gender? = null,
    val role: FamilyRole? = null,
    val childOrder: Int? = null,
    val birthday: LocalDate? = null,
    val isAdmin: Boolean = false,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
    val isDeleted: Boolean = false
)