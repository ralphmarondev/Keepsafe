package com.ralphmarondev.keepsafe.core.data.local.database.mapper

import com.ralphmarondev.keepsafe.core.data.local.database.entity.FamilyEntity
import com.ralphmarondev.keepsafe.core.domain.model.Family

fun Family.toEntity(): FamilyEntity {
    return FamilyEntity(
        id = id,
        familyId = familyId,
        familyName = familyName,
        createdAt = createdAt,
        updatedAt = createdAt,
        isDeleted = isDeleted
    )
}

fun FamilyEntity.toDomain(): Family {
    return Family(
        id = id,
        familyId = familyId,
        familyName = familyName,
        createdAt = createdAt,
        updatedAt = createdAt,
        isDeleted = isDeleted
    )
}