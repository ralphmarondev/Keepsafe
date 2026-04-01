package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.core.di.coreModule
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {
    includes(coreModule)
    includes(platformModule)
}