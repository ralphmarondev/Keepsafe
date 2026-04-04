package com.ralphmarondev.keepsafe.features.family.di

import com.ralphmarondev.keepsafe.features.family.data.repository.FamilyRepositoryImpl
import com.ralphmarondev.keepsafe.features.family.domain.repository.FamilyRepository
import com.ralphmarondev.keepsafe.features.family.presentation.member_list.MemberListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val familyModule = module {
    singleOf(::FamilyRepositoryImpl).bind<FamilyRepository>()
    factoryOf(::MemberListViewModel)
}