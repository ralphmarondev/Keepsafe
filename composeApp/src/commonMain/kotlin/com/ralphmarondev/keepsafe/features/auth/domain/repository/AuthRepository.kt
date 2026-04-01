package com.ralphmarondev.keepsafe.features.auth.domain.repository

import com.ralphmarondev.keepsafe.core.domain.model.Family
import com.ralphmarondev.keepsafe.core.domain.model.Member
import com.ralphmarondev.keepsafe.core.domain.model.Result

interface AuthRepository {
    suspend fun login(email: String, password: String, familyId: String): Result<Member>
    suspend fun register(family: Family, member: Member): Result<Member>
}