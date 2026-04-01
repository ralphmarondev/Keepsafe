package com.ralphmarondev.keepsafe.features.auth.presentation.register

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.core.domain.model.FamilyRole
import com.ralphmarondev.keepsafe.core.domain.model.Gender
import com.ralphmarondev.keepsafe.core.presentation.components.NormalTextField
import com.ralphmarondev.keepsafe.core.presentation.components.PrimaryButton
import com.ralphmarondev.keepsafe.core.presentation.components.SecondaryButton
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreenRoot(
    onRegistrationSuccessful: () -> Unit,
    onLoginClick: () -> Unit
) {
    val viewModel: RegisterViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isRegistered) {
        if (state.isRegistered) {
            onRegistrationSuccessful()
        }
    }

    LaunchedEffect(state.navigateToLogin) {
        if (state.navigateToLogin) {
            onLoginClick()
        }
    }

    RegisterScreen(
        state = state,
        action = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterScreen(
    state: RegisterState,
    action: (RegisterAction) -> Unit
) {
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
                    Text(text = "Register")
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
                        when (state.currentScreen) {
                            Screen.FamilyProfile -> {
                                FamilyProfile(
                                    familyId = state.familyId,
                                    familyName = state.familyName,
                                    familyIdChange = { action(RegisterAction.FamilyIdChange(it)) },
                                    familyNameChange = { action(RegisterAction.FamilyNameChange(it)) },
                                    prev = { action(RegisterAction.Login) },
                                    next = { action(RegisterAction.ScreenChange(Screen.PersonalInformation)) }
                                )
                            }

                            Screen.PersonalInformation -> {
                                PersonalInformation(
                                    firstName = state.firstName,
                                    lastName = state.lastName,
                                    middleName = state.middleName,
                                    maidenName = state.maidenName,
                                    firstNameChange = { action(RegisterAction.FirstNameChange(it)) },
                                    lastNameChange = { action(RegisterAction.LastNameChange(it)) },
                                    middleNameChange = { action(RegisterAction.MiddleNameChange(it)) },
                                    maidenNameChange = { action(RegisterAction.MaidenNameChange(it)) },
                                    prev = { action(RegisterAction.ScreenChange(Screen.FamilyProfile)) },
                                    next = { action(RegisterAction.ScreenChange(Screen.OptionalInformation)) }
                                )
                            }

                            Screen.OptionalInformation -> {
                                OptionalInformation(
                                    birthday = state.birthday,
                                    gender = state.gender,
                                    role = state.role,
                                    birthdayChange = { action(RegisterAction.BirthdayChange(it)) },
                                    genderChange = { action(RegisterAction.GenderChange(it)) },
                                    roleChange = { action(RegisterAction.RoleChange(it)) },
                                    prev = { action(RegisterAction.ScreenChange(Screen.PersonalInformation)) },
                                    next = { action(RegisterAction.ScreenChange(Screen.AccountInformation)) }
                                )
                            }

                            Screen.AccountInformation -> {
                                AccountInformation(
                                    email = state.email,
                                    password = state.password,
                                    emailChange = { action(RegisterAction.EmailChange(it)) },
                                    passwordChange = { action(RegisterAction.PasswordChange(it)) },
                                    prev = { action(RegisterAction.ScreenChange(Screen.OptionalInformation)) },
                                    next = { action(RegisterAction.ScreenChange(Screen.Summary)) }
                                )
                            }

                            Screen.Summary -> {
                                Summary(
                                    register = {},
                                    prev = { action(RegisterAction.ScreenChange(Screen.OptionalInformation)) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FamilyProfile(
    familyId: String,
    familyName: String,
    familyIdChange: (String) -> Unit,
    familyNameChange: (String) -> Unit,
    next: () -> Unit,
    prev: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Create Your Family Profile",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Start by setting up your family's name and unique ID. This will be used to organized and manage your family members.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(16.dp))

    NormalTextField(
        value = familyId,
        onValueChange = { familyIdChange(it) },
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
        value = familyName,
        onValueChange = { familyNameChange(it) },
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
        labelText = "Family Name",
        placeHolderText = "Your Family Name"
    )

    PrimaryButton(
        onClick = { next() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Next"
    )

    SecondaryButton(
        onClick = { prev() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Already have a Family"
    )
}

@Composable
private fun PersonalInformation(
    firstName: String,
    lastName: String,
    middleName: String,
    maidenName: String,
    firstNameChange: (String) -> Unit,
    lastNameChange: (String) -> Unit,
    middleNameChange: (String) -> Unit,
    maidenNameChange: (String) -> Unit,
    next: () -> Unit,
    prev: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Tell Us About You",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Enter your basic information. You'll be the first member and administrator of this family.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(16.dp))

    NormalTextField(
        value = firstName,
        onValueChange = { firstNameChange(it) },
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
        labelText = "First Name",
        placeHolderText = "Your First Name"
    )

    NormalTextField(
        value = lastName,
        onValueChange = { lastNameChange(it) },
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
        labelText = "Last Name",
        placeHolderText = "Your Last Name"
    )

    NormalTextField(
        value = middleName,
        onValueChange = { middleNameChange(it) },
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
        labelText = "Middle Name",
        placeHolderText = "Your Middle Name"
    )

    NormalTextField(
        value = maidenName,
        onValueChange = { maidenNameChange(it) },
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
        labelText = "Maiden Name",
        placeHolderText = "(Optional)"
    )


    PrimaryButton(
        onClick = { next() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Next"
    )

    SecondaryButton(
        onClick = { prev() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Previous"
    )
}

@Composable
private fun OptionalInformation(
    birthday: String,
    gender: Gender,
    role: FamilyRole,
    birthdayChange: (String) -> Unit,
    genderChange: (Gender) -> Unit,
    roleChange: (FamilyRole) -> Unit,
    next: () -> Unit,
    prev: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Tell Us More About Yourself",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Please provide your birthday, role, and gender. This helps us personalize your experience and manage your family account correctly.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(16.dp))

    NormalTextField(
        value = birthday,
        onValueChange = { birthdayChange(it) },
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
        labelText = "Birthday",
        placeHolderText = "YYYY/MM/DD"
    )

    NormalTextField(
        value = "",
        onValueChange = { },
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
        labelText = "Role",
        placeHolderText = ""
    )

    NormalTextField(
        value = "",
        onValueChange = { },
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
        labelText = "Gender",
        placeHolderText = ""
    )

    PrimaryButton(
        onClick = { next() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Next"
    )

    SecondaryButton(
        onClick = { prev() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Previous"
    )
}

@Composable
private fun AccountInformation(
    email: String,
    password: String,
    emailChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    next: () -> Unit,
    prev: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Secure Your Account",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Create your login credentials so you can access and manage your family anytime.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(16.dp))

    NormalTextField(
        value = email,
        onValueChange = { emailChange(it) },
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
        labelText = "Email",
        placeHolderText = "you@example.com"
    )

    NormalTextField(
        value = password,
        onValueChange = { passwordChange(it) },
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
        labelText = "Password",
        placeHolderText = "Your Password"
    )

    PrimaryButton(
        onClick = { next() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Next"
    )

    SecondaryButton(
        onClick = { prev() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Previous"
    )
}

@Composable
private fun Summary(
    register: () -> Unit,
    prev: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Review Your Information",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Take a moment to check your details before completing your registration.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(16.dp))

    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "Family ID:",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "1234",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "Family Name:",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "ABCD",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "Admin Email:",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "you@example.com",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }

    PrimaryButton(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Register"
    )

    SecondaryButton(
        onClick = { prev() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Previous"
    )
}