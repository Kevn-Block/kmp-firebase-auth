package com.medium.authtutorial.login

import kotlinx.serialization.Serializable

sealed class LoginRoutes {
    @Serializable
    data object Welcome : LoginRoutes()

    @Serializable
    data object SignIn : LoginRoutes()

    @Serializable
    data object CreateAccount : LoginRoutes()
}