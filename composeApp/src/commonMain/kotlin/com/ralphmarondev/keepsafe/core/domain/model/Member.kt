package com.ralphmarondev.keepsafe.core.domain.model

enum class Gender {
    Male, Female
}

enum class FamilyRole {
    Father,
    Mother,
    Child,
    Guardian
}

enum class Permission {
    EDIT_SELF,
    EDIT_FAMILY_MEMBER,
    EDIT_ROLE,
    EDIT_BIRTHDAY
}

fun allowedPermissions(member: Member): List<Permission> {
    if (member.isAdmin) return Permission.entries.toList()

    return when (member.role) {
        FamilyRole.Father, FamilyRole.Mother -> Permission.entries.toList()
        FamilyRole.Child -> listOf(Permission.EDIT_SELF)
        FamilyRole.Guardian -> listOf(Permission.EDIT_SELF, Permission.EDIT_BIRTHDAY)
        null -> listOf(Permission.EDIT_SELF)
    }
}

data class Member(
    val id: Long = 0,
    val localFamilyId: Long = 0, // from local database
    val familyId: String = "", // from firebase
    val email: String = "",
    val password: String = "",
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val maidenName: String? = null,
    val gender: Gender? = null,
    val role: FamilyRole? = null,
    val childOrder: Int? = null,
    val birthday: String? = null,
    val isAdmin: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(), //Clock.System.now(),
    val updatedAt: Long = System.currentTimeMillis(), //Clock.System.now(),
    val isDeleted: Boolean = false
)