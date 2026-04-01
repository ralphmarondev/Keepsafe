package com.ralphmarondev.keepsafe.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ralphmarondev.keepsafe.core.data.local.database.entity.MemberEntity

@Dao
interface MemberDao {
    @Insert
    suspend fun insert(member: MemberEntity): Long

    @Query("SELECT * FROM members WHERE familyId = :familyId AND isDeleted = 0")
    suspend fun getMembers(familyId: Long): List<MemberEntity>
}