package com.ralphmarondev.keepsafe.features.family.domain.repository

import com.ralphmarondev.keepsafe.core.domain.model.Member
import com.ralphmarondev.keepsafe.core.domain.model.Result

interface FamilyRepository {
    suspend fun getAllMembers(): List<Member>
    suspend fun addNewMember(member: Member): Result<Member>
}