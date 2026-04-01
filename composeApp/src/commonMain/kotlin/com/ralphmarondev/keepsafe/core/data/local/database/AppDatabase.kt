package com.ralphmarondev.keepsafe.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ralphmarondev.keepsafe.core.data.local.database.dao.FamilyDao
import com.ralphmarondev.keepsafe.core.data.local.database.dao.MemberDao
import com.ralphmarondev.keepsafe.core.data.local.database.entity.FamilyEntity
import com.ralphmarondev.keepsafe.core.data.local.database.entity.MemberEntity

@Database(
    entities = [
        FamilyEntity::class,
        MemberEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val familyDao: FamilyDao
    abstract val memberDao: MemberDao

    companion object {
        const val DB_NAME = "keepsafe.db"
    }
}