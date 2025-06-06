package com.medium.authtutorial.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

interface AppCoroutineDispatcher {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}

class AppCoroutineDispatcherImpl : AppCoroutineDispatcher {
    override val io =
        Dispatchers.IO
    override val main =
        Dispatchers.Main
    override val default =
        Dispatchers.Default
}