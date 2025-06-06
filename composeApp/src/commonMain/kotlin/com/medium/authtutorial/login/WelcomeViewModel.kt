package com.medium.authtutorial.login

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import authtutorial.composeapp.generated.resources.Res
import authtutorial.composeapp.generated.resources.user_not_logged_in
import authtutorial.composeapp.generated.resources.you_must_enter_display_name
import com.medium.authtutorial.coroutines.AppCoroutineDispatcher
import com.medium.authtutorial.firebase.AppUser
import com.medium.authtutorial.firebase.FirebaseService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

class WelcomeViewModel(
    private val coroutineDispatcher: AppCoroutineDispatcher,
    private val firebaseService: FirebaseService,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<Effect>()
    val effects = _effects.asSharedFlow()

    fun load() {
        val firebaseApp = firebaseService.getCurrentApp()
        val loggedInUser = firebaseService.getCurrentUser()
        _state.update {
            it.copy(
                projectName = firebaseApp.toString(),
                user = loggedInUser,
            )
        }
    }

    fun onConfirmDisplayName() {
        viewModelScope.launch(coroutineDispatcher.io) {
            val currentUser = state.value.user
            val displayName = state.value.displayNameTextFieldValue.text

            if (currentUser == null) {
                _state.update {
                    it.copy(displayNameError = getString(Res.string.user_not_logged_in))
                }
                return@launch
            }

            if (displayName.isBlank()) {
                _state.update {
                    it.copy(displayNameError = getString(Res.string.you_must_enter_display_name))
                }
                return@launch
            }

            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        displayNameError = null
                    )
                }
                firebaseService.setDisplayName(displayName = displayName)
                val newUser = currentUser.copy(displayName = displayName)
                _state.update {
                    it.copy(
                        user = newUser,
                        isLoading = false,
                    )
                }
            } catch (exception: Exception) {
                _state.update { it.copy(displayNameError = "Error: ${exception.message}") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onChangeDisplayName(displayName: TextFieldValue) {
        _state.update { it.copy(displayNameTextFieldValue = displayName) }
    }

    fun signOut() {
        viewModelScope.launch(coroutineDispatcher.io) {
            firebaseService.signOut()
            _state.update {
                it.copy(
                    user = null,
                )
            }
        }
    }

    fun goToSignIn() {
        viewModelScope.launch(coroutineDispatcher.io) {
            _effects.emit(Effect.GoToSignIn)
        }
    }

    fun goToCreateAccount() {
        viewModelScope.launch(coroutineDispatcher.io) {
            _effects.emit(Effect.GoToCreateAccount)
        }
    }

    data class State(
        val projectName: String = "",
        val user: AppUser? = null,
        val isLoading: Boolean = false,
        val displayNameError: String? = null,
        val displayNameTextFieldValue: TextFieldValue = TextFieldValue(),
    )

    sealed class Effect {
        data object GoToSignIn : Effect()
        data object GoToCreateAccount : Effect()
    }
}
