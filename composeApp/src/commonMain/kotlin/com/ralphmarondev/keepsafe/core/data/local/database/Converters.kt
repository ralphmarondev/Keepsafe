package com.ralphmarondev.keepsafe.core.data.local.database

import androidx.room.TypeConverter
import com.ralphmarondev.keepsafe.core.domain.model.FamilyRole
import com.ralphmarondev.keepsafe.core.domain.model.Gender
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

class Converters {

    @TypeConverter
    fun fromGender(gender: Gender?): String? = gender?.name

    @TypeConverter
    fun toGender(value: String?): Gender? = value?.let { Gender.valueOf(it) }

    @TypeConverter
    fun fromRole(role: FamilyRole?): String? = role?.name

    @TypeConverter
    fun toRole(value: String?): FamilyRole? = value?.let { FamilyRole.valueOf(it) }

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromInstant(instant: Instant?): Long? = instant?.toEpochMilliseconds()

    @TypeConverter
    fun toInstant(ms: Long?): Instant? = ms?.let { Instant.fromEpochSeconds(it) }
}