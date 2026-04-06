package com.ralphmarondev.keepsafe.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ralphmarondev.keepsafe.core.data.local.database.entity.MemberEntity

@Dao
interface MemberDao {
    @Insert
    suspend fun insert(member: MemberEntity): Long

    @Query("SELECT * FROM members WHERE email = :email AND password = :password AND firebaseFamilyId = :familyId AND isDeleted = 0")
    suspend fun login(email: String, password: String, familyId: String): MemberEntity?

    @Query("SELECT * FROM members WHERE localFamilyId = :familyId AND isDeleted = 0")
    suspend fun getMembersByLocalFamilyId(familyId: Long): List<MemberEntity>

    @Query("SELECT * FROM members WHERE firebaseFamilyId = :familyId AND isDeleted = 0")
    suspend fun getMembersByFirebaseFamilyId(familyId: String): List<MemberEntity>
}