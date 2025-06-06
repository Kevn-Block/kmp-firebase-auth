package com.medium.authtutorial

import androidx.compose.ui.window.ComposeUIViewController
import com.medium.authtutorial.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() },
    content = {
        App()
    }
)