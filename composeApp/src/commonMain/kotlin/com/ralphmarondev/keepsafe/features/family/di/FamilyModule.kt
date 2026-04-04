package com.ralphmarondev.keepsafe.features.family.di

import com.ralphmarondev.keepsafe.features.family.presentation.member_list.MemberListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val familyModule = module {
    factoryOf(::MemberListViewModel)
}