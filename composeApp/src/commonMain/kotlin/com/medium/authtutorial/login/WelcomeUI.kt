package com.medium.authtutorial.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import authtutorial.composeapp.generated.resources.Res
import authtutorial.composeapp.generated.resources.change_display_name
import authtutorial.composeapp.generated.resources.display_name
import authtutorial.composeapp.generated.resources.loading
import authtutorial.composeapp.generated.resources.sign_in
import authtutorial.composeapp.generated.resources.sign_out
import authtutorial.composeapp.generated.resources.sign_up
import authtutorial.composeapp.generated.resources.user_not_logged_in
import com.medium.authtutorial.firebase.AppUser
import com.medium.authtutorial.theme.AuthTutorialTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WelcomeUI(
    viewModel: WelcomeViewModel = koinViewModel(),
    onNavigateToSignIn: () -> Unit,
    onNavigateToCreateAccount: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.load()
    }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is WelcomeViewModel.Effect.GoToSignIn ->
                    onNavigateToSignIn()

                is WelcomeViewModel.Effect.GoToCreateAccount ->
                    onNavigateToCreateAccount()
            }
        }
    }

    WelcomeContent(
        projectName = state.projectName,
        displayNameTextFieldValue = state.displayNameTextFieldValue,
        displayNameError = state.displayNameError,
        isLoading = state.isLoading,
        loggedInUser = state.user,
        onChangeDisplayName = viewModel::onChangeDisplayName,
        onConfirmDisplayName = viewModel::onConfirmDisplayName,
        onSignOut = viewModel::signOut,
        goToSignIn = viewModel::goToSignIn,
        goToCreateAccount = viewModel::goToCreateAccount,
    )
}

@Composable
fun WelcomeContent(
    projectName: String,
    displayNameTextFieldValue: TextFieldValue,
    isLoading: Boolean,
    loggedInUser: AppUser?,
    displayNameError: String?,
    onChangeDisplayName: (TextFieldValue) -> Unit,
    onConfirmDisplayName: () -> Unit,
    onSignOut: () -> Unit,
    goToSignIn: () -> Unit,
    goToCreateAccount: () -> Unit,
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .systemBarsPadding()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = projectName,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = goToSignIn,
                ) {
                    Text(stringResource(Res.string.sign_in))
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = goToCreateAccount,
                ) {
                    Text(stringResource(Res.string.sign_up))
                }

                Spacer(Modifier.height(8.dp))

                if (loggedInUser != null) {
                    UserSignedInDetails(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        displayNameTextFieldValue = displayNameTextFieldValue,
                        displayNameError = displayNameError,
                        userGreeting = loggedInUser.greeting(),
                        isLoading = isLoading,
                        onDisplayNameChanged = onChangeDisplayName,
                        onConfirmDisplayName = onConfirmDisplayName,
                        onSignOut = onSignOut
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(Res.string.user_not_logged_in),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun UserSignedInDetails(
    modifier: Modifier,
    displayNameTextFieldValue: TextFieldValue,
    displayNameError: String?,
    userGreeting: String,
    isLoading: Boolean,
    onDisplayNameChanged: (TextFieldValue) -> Unit,
    onConfirmDisplayName: () -> Unit,
    onSignOut: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp),
            text = userGreeting,
            style = MaterialTheme.typography.bodyLarge,
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
        )
        if (displayNameError != null) {
            Text(
                text = displayNameError,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            onClick = onConfirmDisplayName,
            enabled = !isLoading
        ) {
            if (isLoading) {
                Text(stringResource(Res.string.loading))
            } else {
                Text(stringResource(Res.string.change_display_name))
            }
        }
        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            onClick = onSignOut,
        ) {
            Text(stringResource(Res.string.sign_out))
        }
    }
}

@Preview
@Composable
fun WelcomeContent_Preview() {
    AuthTutorialTheme {
        WelcomeContent(
            projectName = "Test Project",
            displayNameTextFieldValue = TextFieldValue("User"),
            loggedInUser = AppUser(
                userId = "123",
                displayName = "User",
                email = "testing@gmail.com"
            ),
            isLoading = false,
            displayNameError = null,
            onChangeDisplayName = {},
            goToSignIn = {},
            goToCreateAccount = {},
            onSignOut = {},
            onConfirmDisplayName = {}
        )
    }
}