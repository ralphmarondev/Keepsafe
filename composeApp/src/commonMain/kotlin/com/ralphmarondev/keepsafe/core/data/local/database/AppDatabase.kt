package com.ralphmarondev.keepsafe.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ralphmarondev.keepsafe.core.data.local.database.dao.UserDao
import com.ralphmarondev.keepsafe.core.data.local.database.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        const val DB_NAME = "keepsafe.db"
    }
}