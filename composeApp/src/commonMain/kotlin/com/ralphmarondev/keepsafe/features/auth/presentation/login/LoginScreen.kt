package com.ralphmarondev.keepsafe.features.auth.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.core.presentation.components.NormalTextField
import com.ralphmarondev.keepsafe.core.presentation.components.PasswordField
import com.ralphmarondev.keepsafe.core.presentation.components.PrimaryButton
import com.ralphmarondev.keepsafe.core.presentation.components.SecondaryButton
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreenRoot(
    onLoginSuccessful: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateToRegister) {
        if (state.navigateToRegister) {
            onRegisterClick()
        }
    }

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onLoginSuccessful()
        }
    }

    LoginScreen(
        state = state,
        action = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreen(
    state: LoginState,
    action: (LoginAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val hostState = remember { SnackbarHostState() }

    LaunchedEffect(state.showMessage) {
        if (state.showMessage) {
            state.message?.let {
                hostState.showSnackbar(message = it)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Login")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = hostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp)
        ) {
            item {
                OutlinedCard(
                    modifier = Modifier.widthIn(max = 500.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Welcome Back",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Log in to your account to continue.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        NormalTextField(
                            value = state.familyId,
                            onValueChange = { action(LoginAction.FamilyIdChange(it)) },
                            modifier = Modifier
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Next)
                                }
                            ),
                            leadingIconImageVector = Icons.Outlined.AccountTree,
                            labelText = "Family ID",
                            placeHolderText = "your-family-id"
                        )
                        NormalTextField(
                            value = state.email,
                            onValueChange = { action(LoginAction.EmailChange(it)) },
                            modifier = Modifier
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Next)
                                }
                            ),
                            leadingIconImageVector = Icons.Outlined.Email,
                            labelText = "Email",
                            placeHolderText = "you@example.com"
                        )
                        PasswordField(
                            value = state.password,
                            onValueChange = { action(LoginAction.PasswordChange(it)) },
                            modifier = Modifier
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            labelText = "Password"
                        )

                        PrimaryButton(
                            onClick = { action(LoginAction.Login) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            enabled = !state.isLoggingIn,
                            defaultText = "Login",
                            disabledText = "Logging in..."
                        )

                        SecondaryButton(
                            onClick = { action(LoginAction.Register) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            defaultText = "Register New Family"
                        )
                    }
                }
            }
        }
    }
}