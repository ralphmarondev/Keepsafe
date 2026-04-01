package com.ralphmarondev.keepsafe.features.auth.di

import com.ralphmarondev.keepsafe.features.auth.data.repository.AuthRepositoryImpl
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.features.auth.presentation.login.LoginViewModel
import com.ralphmarondev.keepsafe.features.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    factoryOf(::LoginViewModel)
    factoryOf(::RegisterViewModel)
}