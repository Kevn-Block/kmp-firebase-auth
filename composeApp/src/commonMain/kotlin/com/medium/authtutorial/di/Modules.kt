package com.medium.authtutorial.di

import com.medium.authtutorial.coroutines.AppCoroutineDispatcher
import com.medium.authtutorial.coroutines.AppCoroutineDispatcherImpl
import com.medium.authtutorial.firebase.FirebaseService
import com.medium.authtutorial.firebase.FirebaseServiceImpl
import com.medium.authtutorial.login.CreateAccountViewModel
import com.medium.authtutorial.login.SignInViewModel
import com.medium.authtutorial.login.WelcomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::FirebaseServiceImpl).bind<FirebaseService>()
    viewModelOf(::WelcomeViewModel).bind<WelcomeViewModel>()
    viewModelOf(::SignInViewModel).bind<SignInViewModel>()
    viewModelOf(::CreateAccountViewModel).bind<CreateAccountViewModel>()
    singleOf(::AppCoroutineDispatcherImpl).bind<AppCoroutineDispatcher>()
}