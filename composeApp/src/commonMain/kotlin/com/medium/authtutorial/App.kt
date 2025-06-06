package com.medium.authtutorial

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.medium.authtutorial.login.CreateAccountUI
import com.medium.authtutorial.login.LoginRoutes
import com.medium.authtutorial.login.WelcomeUI
import com.medium.authtutorial.theme.AuthTutorialTheme
import com.medium.authtutorial.login.SignInUI


@Composable
fun App() {
    AuthTutorialTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = LoginRoutes.Welcome) {
            composable<LoginRoutes.Welcome> {
                WelcomeUI(
                    onNavigateToSignIn = {
                        navController.navigate(LoginRoutes.SignIn)
                    },
                    onNavigateToCreateAccount = {
                        navController.navigate(LoginRoutes.CreateAccount)
                    },
                )
            }
            composable<LoginRoutes.SignIn> {
                SignInUI(
                    onSignIn = {
                        navController.popBackStack()
                    },
                    onGoBack = {
                        navController.popBackStack()
                    },
                    onGoToSignup = {
                        navController.popBackStack()
                        navController.navigate(LoginRoutes.CreateAccount)
                    }
                )
            }
            composable<LoginRoutes.CreateAccount> {
                CreateAccountUI(
                    onAccountCreated = {
                        navController.popBackStack()
                    },
                    onGoBack = {
                        navController.popBackStack()
                    },
                    onGoToSignIn = {
                        navController.popBackStack()
                        navController.navigate(LoginRoutes.SignIn)
                    }
                )
            }
        }
    }
}