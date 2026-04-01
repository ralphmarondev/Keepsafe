package com.ralphmarondev.keepsafe.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ralphmarondev.keepsafe.core.data.local.database.entity.FamilyEntity

@Dao
interface FamilyDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(family: FamilyEntity): Long

    @Query("SELECT * FROM families WHERE isDeleted = 0")
    suspend fun getAllFamilies(): List<FamilyEntity>
}