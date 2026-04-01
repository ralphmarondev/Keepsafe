package com.ralphmarondev.keepsafe.core.data.local.database.mapper

import com.ralphmarondev.keepsafe.core.data.local.database.entity.MemberEntity
import com.ralphmarondev.keepsafe.core.domain.model.Member

fun Member.toEntity(): MemberEntity {
    return MemberEntity(
        id = id,
        localFamilyId = localFamilyId,
        familyId = familyId,
        email = email,
        password = password,
        firstName = firstName,
        lastName = lastName,
        middleName = middleName,
        maidenName = maidenName,
        gender = gender,
        role = role,
        childOrder = childOrder,
        birthday = birthday,
        isAdmin = isAdmin,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted
    )
}

fun MemberEntity.toDomain(): Member {
    return Member(
        id = id,
        localFamilyId = localFamilyId,
        familyId = familyId,
        email = email,
        password = password,
        firstName = firstName,
        lastName = lastName,
        middleName = middleName,
        maidenName = maidenName,
        gender = gender,
        role = role,
        childOrder = childOrder,
        birthday = birthday,
        isAdmin = isAdmin,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted
    )
}