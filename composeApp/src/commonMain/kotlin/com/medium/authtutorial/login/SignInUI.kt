package com.medium.authtutorial.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import authtutorial.composeapp.generated.resources.Res
import authtutorial.composeapp.generated.resources.email
import authtutorial.composeapp.generated.resources.loading
import authtutorial.composeapp.generated.resources.no_account_signed_up
import authtutorial.composeapp.generated.resources.password
import authtutorial.composeapp.generated.resources.sign_in
import com.medium.authtutorial.components.SecureTextField
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInUI(
    viewModel: SignInViewModel = koinViewModel<SignInViewModel>(),
    onSignIn: () -> Unit,
    onGoBack: () -> Unit,
    onGoToSignup: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                SignInViewModel.Effect.SignInSuccess ->
                    onSignIn()

                SignInViewModel.Effect.GoToSignUp ->
                    onGoToSignup()

                SignInViewModel.Effect.GoBack ->
                    onGoBack()
            }
        }
    }


    BackHandler(enabled = true) {
        viewModel.goBack()
    }

    SignInContent(
        emailTextFieldValue = state.emailTextFieldValue,
        passwordTextFieldValue = state.passwordTextFieldValue,
        loginError = state.error,
        isLoading = state.isLoading,
        onSignIn = viewModel::signIn,
        goToSignUp = viewModel::goToSignUp,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged
    )
}

@Composable
fun SignInContent(
    emailTextFieldValue: TextFieldValue,
    passwordTextFieldValue: TextFieldValue,
    loginError: String?,
    isLoading: Boolean,
    onEmailChanged: (TextFieldValue) -> Unit,
    onPasswordChanged: (TextFieldValue) -> Unit,
    onSignIn: () -> Unit,
    goToSignUp: () -> Unit,
) {
    Scaffold {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .padding(16.dp)
        ) {

            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp),
                text = stringResource(Res.string.sign_in),
                style = MaterialTheme.typography.displayMedium
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = emailTextFieldValue,
                onValueChange = onEmailChanged,
                singleLine = true,
                label = {
                    Text(stringResource(Res.string.email))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            SecureTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = passwordTextFieldValue,
                onValueChange = onPasswordChanged,
                label = stringResource(Res.string.password),
                imeAction = ImeAction.Done
            )
            if (loginError != null) {
                Text(
                    text = loginError,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Button(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                onClick = onSignIn,
                enabled = !isLoading
            ) {
                if (isLoading) {
                    Text(stringResource(Res.string.loading))
                } else {
                    Text(stringResource(Res.string.sign_in))
                }
            }
            Button(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                onClick = goToSignUp,
            ) {
                Text(stringResource(Res.string.no_account_signed_up))
            }
        }
    }
}