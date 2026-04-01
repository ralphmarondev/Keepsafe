package com.ralphmarondev.keepsafe.core.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        return Room.databaseBuilder(
            context = context.applicationContext,
            name = context.getDatabasePath(AppDatabase.DB_NAME).absolutePath
        )
    }
}