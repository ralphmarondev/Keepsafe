package com.ralphmarondev.keepsafe.core.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String = "",
    val password: String = "", // only used on login
    val fullName: String = "",
    val createDate: Long = System.currentTimeMillis(),
    val lastUpdateDate: Long = 0
)