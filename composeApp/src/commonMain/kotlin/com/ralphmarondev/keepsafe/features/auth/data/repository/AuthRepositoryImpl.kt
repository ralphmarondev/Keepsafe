package com.ralphmarondev.keepsafe.features.auth.data.repository

import com.ralphmarondev.keepsafe.core.domain.model.Member
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
        familyId: Long
    ): Result<Member> {
        val member = Member(
            email = email,
            password = password,
            familyId = familyId
        )
        return Result.Success(member)
    }

    override suspend fun register(member: Member): Result<Member> {
        return Result.Success(member)
    }
}