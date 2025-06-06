package com.medium.authtutorial.login

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medium.authtutorial.coroutines.AppCoroutineDispatcher
import com.medium.authtutorial.firebase.FirebaseService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val coroutineDispatcher: AppCoroutineDispatcher,
    private val firebaseService: FirebaseService
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<Effect>()
    val effects = _effects.asSharedFlow()

    fun signIn() {
        val email = state.value.emailTextFieldValue.text
        val password = state.value.passwordTextFieldValue.text
        viewModelScope.launch(coroutineDispatcher.io) {
            try {
                _state.update {
                    it.copy(isLoading = true, error = null)
                }
                firebaseService.signIn(email = email, password = password)
                _effects.emit(Effect.SignInSuccess)
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

    fun goToSignUp() {
        viewModelScope.launch(coroutineDispatcher.io) {
            _effects.emit(Effect.GoToSignUp)
        }
    }

    fun goBack() {
        viewModelScope.launch(coroutineDispatcher.io) {
            _effects.emit(Effect.GoBack)
        }
    }

    fun onPasswordChanged(password: TextFieldValue) {
        _state.update { it.copy(passwordTextFieldValue = password) }
    }

    fun onEmailChanged(email: TextFieldValue) {
        _state.update { it.copy(emailTextFieldValue = email) }
    }

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val emailTextFieldValue: TextFieldValue = TextFieldValue(),
        val passwordTextFieldValue: TextFieldValue = TextFieldValue(),
    )

    sealed class Effect {
        data object SignInSuccess : Effect()
        data object GoToSignUp : Effect()
        data object GoBack : Effect()
    }
}