package com.ralphmarondev.keepsafe.features.family.data.repository

import com.ralphmarondev.keepsafe.core.data.local.database.dao.MemberDao
import com.ralphmarondev.keepsafe.core.data.local.database.mapper.toDomain
import com.ralphmarondev.keepsafe.core.data.local.database.mapper.toEntity
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.domain.model.Member
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.features.family.domain.repository.FamilyRepository
import kotlinx.coroutines.flow.first

class FamilyRepositoryImpl(
    private val memberDao: MemberDao,
    private val preferences: AppPreferences
) : FamilyRepository {
    override suspend fun getAllMembers(): List<Member> {
        val firebaseFamilyId = preferences.currentFirebaseFamilyId.first()

        return memberDao.getMembersByFirebaseFamilyId(firebaseFamilyId)
            .map { it.toDomain() }
    }

    override suspend fun addNewMember(member: Member): Result<Member> {
        val firebaseFamilyId = preferences.currentFirebaseFamilyId.first()
        val newMember = member.copy(
            firebaseFamilyId = firebaseFamilyId
        )
        memberDao.insert(newMember.toEntity())

        return Result.Success(newMember)
    }
}