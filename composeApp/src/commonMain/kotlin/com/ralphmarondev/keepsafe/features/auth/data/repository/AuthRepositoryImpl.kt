package com.ralphmarondev.keepsafe.features.auth.data.repository

import com.ralphmarondev.keepsafe.core.data.local.database.dao.FamilyDao
import com.ralphmarondev.keepsafe.core.data.local.database.dao.MemberDao
import com.ralphmarondev.keepsafe.core.data.local.database.mapper.toDomain
import com.ralphmarondev.keepsafe.core.data.local.database.mapper.toEntity
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.domain.model.Family
import com.ralphmarondev.keepsafe.core.domain.model.Member
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val familyDao: FamilyDao,
    private val memberDao: MemberDao,
    private val preferences: AppPreferences
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
        familyId: String
    ): Result<Member> {
        val memberEntity = memberDao.login(
            email = email,
            password = password,
            familyId = familyId
        )
        if (memberEntity == null) {
            return Result.Error("Invalid credentials.")
        }

        val member = memberEntity.toDomain()
        /*
         * Login on firebase with email and password.
         * Check on firestore if the user (email) belong the family (familyId).
         * If Yes:
         *      - Download and save all family information to local device.
         * Else:
         *      - Display invalid credentials.
         */
        // NOTE: save to preference so no login needed on next app launch
        preferences.setCurrentUser(member.email)
        preferences.setFirebaseFamilyId(member.firebaseFamilyId)
        preferences.setAuthenticated(true)

        return Result.Success(member)
    }

    override suspend fun register(
        family: Family,
        member: Member
    ): Result<Member> {
        val localFamilyId = familyDao.insert(family.toEntity())
        val newMember = member.copy(
            localFamilyId = localFamilyId
        )
        memberDao.insert(newMember.toEntity())

        // NOTE: save to preference so no login needed on next app launch
        preferences.setCurrentUser(newMember.email)
        preferences.setLocalFamilyId(newMember.localFamilyId)
        preferences.setFirebaseFamilyId(newMember.firebaseFamilyId)
        preferences.setAuthenticated(true)

        return Result.Success(newMember)
    }
}