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
import authtutorial.composeapp.generated.resources.confirm_password
import authtutorial.composeapp.generated.resources.create_account
import authtutorial.composeapp.generated.resources.display_name
import authtutorial.composeapp.generated.resources.email
import authtutorial.composeapp.generated.resources.have_an_account
import authtutorial.composeapp.generated.resources.loading
import authtutorial.composeapp.generated.resources.password
import com.medium.authtutorial.components.SecureTextField
import com.medium.authtutorial.theme.AuthTutorialTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateAccountUI(
    viewModel: CreateAccountViewModel = koinViewModel(),
    onAccountCreated: () -> Unit,
    onGoToSignIn: () -> Unit,
    onGoBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                CreateAccountViewModel.Effect.CreateAccountSuccess ->
                    onAccountCreated()

                CreateAccountViewModel.Effect.GoToSignIn ->
                    onGoToSignIn()

                CreateAccountViewModel.Effect.GoBack ->
                    onGoBack()
            }
        }
    }


    BackHandler(enabled = true) {
        viewModel.goBack()
    }

    CreateAccountContent(
        emailTextFieldValue = state.emailTextFieldValue,
        passwordTextFieldValue = state.passwordTextFieldValue,
        confirmPasswordTextFieldValue = state.confirmPasswordTextFieldValue,
        displayNameTextFieldValue = state.displayNameTextFieldValue,
        error = state.error,
        isLoading = state.isLoading,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onConfirmPasswordChanged = viewModel::onConfirmPasswordChanged,
        onDisplayNameChanged = viewModel::onDisplayNameChanged,
        onCreateAccount = viewModel::createAccount,
        onGoToSignIn = viewModel::goToSignIn,
    )
}

@Composable
fun CreateAccountContent(
    emailTextFieldValue: TextFieldValue,
    passwordTextFieldValue: TextFieldValue,
    confirmPasswordTextFieldValue: TextFieldValue,
    displayNameTextFieldValue: TextFieldValue,
    onEmailChanged: (TextFieldValue) -> Unit,
    onPasswordChanged: (TextFieldValue) -> Unit,
    onConfirmPasswordChanged: (TextFieldValue) -> Unit,
    onDisplayNameChanged: (TextFieldValue) -> Unit,
    error: String?,
    isLoading: Boolean,
    onCreateAccount: () -> Unit,
    onGoToSignIn: () -> Unit,
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
                text = stringResource(Res.string.create_account),
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
                imeAction = ImeAction.Next
            )
            SecureTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = confirmPasswordTextFieldValue,
                onValueChange = onConfirmPasswordChanged,
                label = stringResource(Res.string.confirm_password),
                imeAction = ImeAction.Next
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = displayNameTextFieldValue,
                onValueChange = onDisplayNameChanged,
                singleLine = true,
                label = {
                    Text(stringResource(Res.string.display_name))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
            if (error != null) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Button(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                onClick = onCreateAccount,
                enabled = !isLoading
            ) {
                if (isLoading) {
                    Text(stringResource(Res.string.loading))
                } else {
                    Text(stringResource(Res.string.create_account))
                }
            }
            Button(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                onClick = onGoToSignIn,
            ) {
                Text(stringResource(Res.string.have_an_account))
            }
        }
    }
}

@Preview
@Composable
fun CreateAccountContent_Preview() {
    AuthTutorialTheme {
        CreateAccountContent(
            emailTextFieldValue = TextFieldValue("test@test.com"),
            passwordTextFieldValue = TextFieldValue("password123"),
            confirmPasswordTextFieldValue = TextFieldValue("password123"),
            displayNameTextFieldValue = TextFieldValue("Test User"),
            onEmailChanged = {},
            onPasswordChanged = {},
            onConfirmPasswordChanged = {},
            onDisplayNameChanged = {},
            error = null,
            isLoading = false,
            onCreateAccount = {},
            onGoToSignIn = {}
        )
    }
}