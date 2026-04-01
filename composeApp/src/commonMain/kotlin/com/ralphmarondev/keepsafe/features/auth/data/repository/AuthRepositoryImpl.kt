package com.ralphmarondev.keepsafe.features.auth.data.repository

import com.ralphmarondev.keepsafe.core.data.local.database.dao.FamilyDao
import com.ralphmarondev.keepsafe.core.data.local.database.dao.MemberDao
import com.ralphmarondev.keepsafe.core.data.local.database.mapper.toEntity
import com.ralphmarondev.keepsafe.core.domain.model.Family
import com.ralphmarondev.keepsafe.core.domain.model.Member
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val familyDao: FamilyDao,
    private val memberDao: MemberDao
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
        familyId: String
    ): Result<Member> {
        val member = Member(
            email = email,
            password = password,
            familyId = familyId
        )
        return Result.Success(member)
    }

    override suspend fun register(
        family: Family,
        member: Member
    ): Result<Member> {
        val familyId = familyDao.insert(family.toEntity())
        val newMember = member.copy(
            localFamilyId = familyId
        )
        memberDao.insert(newMember.toEntity())

        // NOTE: save to preference so no login needed on next app launch

        return Result.Success(member)
    }
}