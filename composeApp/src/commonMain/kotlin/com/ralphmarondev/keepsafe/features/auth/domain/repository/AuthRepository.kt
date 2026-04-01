package com.ralphmarondev.keepsafe.features.auth.domain.repository

import com.ralphmarondev.keepsafe.core.domain.model.Member
import com.ralphmarondev.keepsafe.core.domain.model.Result

interface AuthRepository {
    suspend fun login(email: String, password: String, familyId: Long): Result<Member>
    suspend fun register(member: Member): Result<Member>
}