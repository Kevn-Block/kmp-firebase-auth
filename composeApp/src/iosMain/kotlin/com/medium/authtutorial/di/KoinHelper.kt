package com.medium.authtutorial.di

import org.koin.core.context.startKoin

fun initKoin() = startKoin {
    modules(
        sharedModule
    )
}