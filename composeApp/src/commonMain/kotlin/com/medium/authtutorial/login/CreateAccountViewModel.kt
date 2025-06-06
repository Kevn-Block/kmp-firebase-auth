package com.medium.authtutorial.login

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import authtutorial.composeapp.generated.resources.Res
import authtutorial.composeapp.generated.resources.passwords_do_not_match
import authtutorial.composeapp.generated.resources.you_must_enter_display_name
import com.medium.authtutorial.coroutines.AppCoroutineDispatcher
import com.medium.authtutorial.firebase.FirebaseService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

class CreateAccountViewModel(
    private val coroutineDispatcher: AppCoroutineDispatcher,
    private val firebaseService: FirebaseService
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<Effect>()
    val effects = _effects.asSharedFlow()

    fun createAccount() {
        val email = state.value.emailTextFieldValue.text
        val password = state.value.passwordTextFieldValue.text
        val displayName = state.value.displayNameTextFieldValue.text

        viewModelScope.launch(coroutineDispatcher.io) {
            if (!isValidAccountDetails()) {
                return@launch
            }
            try {
                _state.update {
                    it.copy(isLoading = true, error = null)
                }
                firebaseService.createUser(
                    email = email,
                    password = password,
                    displayName = displayName
                )
                _effects.emit(Effect.CreateAccountSuccess)
            } catch (exception: Exception) {
                _state.update {
                    it.copy(
                        error = exception.message,
                        isLoading = false,
                    )
                }
            } finally {
                _state.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun goToSignIn() {
        viewModelScope.launch(coroutineDispatcher.io) {
            _effects.emit(Effect.GoToSignIn)
        }
    }

    fun goBack() {
        viewModelScope.launch(coroutineDispatcher.io) {
            _effects.emit(Effect.GoBack)
        }
    }

    fun onEmailChanged(email: TextFieldValue) {
        _state.update { it.copy(emailTextFieldValue = email) }
    }

    fun onPasswordChanged(password: TextFieldValue) {
        _state.update { it.copy(passwordTextFieldValue = password) }
    }

    fun onConfirmPasswordChanged(confirmPassword: TextFieldValue) {
        _state.update { it.copy(confirmPasswordTextFieldValue = confirmPassword) }
    }

    fun onDisplayNameChanged(displayName: TextFieldValue) {
        _state.update { it.copy(displayNameTextFieldValue = displayName) }
    }

    private suspend fun isValidAccountDetails(): Boolean {
        val password = state.value.passwordTextFieldValue.text
        val displayName = state.value.displayNameTextFieldValue.text
        val confirmedPassword = state.value.confirmPasswordTextFieldValue.text

        if (password != confirmedPassword) {
            _state.update {
                it.copy(error = getString(Res.string.passwords_do_not_match), isLoading = false)
            }
            return false
        }

        if (displayName.isBlank()) {
            _state.update {
                it.copy(
                    error = getString(Res.string.you_must_enter_display_name),
                    isLoading = false
                )
            }
            return false
        }

        return true
    }

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val emailTextFieldValue: TextFieldValue = TextFieldValue(),
        val passwordTextFieldValue: TextFieldValue = TextFieldValue(),
        val confirmPasswordTextFieldValue: TextFieldValue = TextFieldValue(),
        val displayNameTextFieldValue: TextFieldValue = TextFieldValue()
    )

    sealed class Effect {
        data object CreateAccountSuccess : Effect()
        data object GoToSignIn : Effect()
        data object GoBack : Effect()
    }
}