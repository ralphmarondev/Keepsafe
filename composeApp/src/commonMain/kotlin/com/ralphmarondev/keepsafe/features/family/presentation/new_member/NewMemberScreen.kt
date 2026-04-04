package com.ralphmarondev.keepsafe.features.family.presentation.new_member

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
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
import com.ralphmarondev.keepsafe.core.presentation.components.DropDownSelector
import com.ralphmarondev.keepsafe.core.presentation.components.NormalTextField
import com.ralphmarondev.keepsafe.core.presentation.components.PrimaryButton
import com.ralphmarondev.keepsafe.core.presentation.components.SecondaryButton
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewMemberScreenRoot(
    navigateBack: () -> Unit
) {
    val viewModel: NewMemberViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
            viewModel.onAction(NewMemberAction.ClearNavigation)
        }
    }

    NewMemberScreen(
        state = state,
        action = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewMemberScreen(
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
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
                    Text(text = "New Family Member")
                },
                navigationIcon = {
                    IconButton(onClick = { action(NewMemberAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = hostState) { data ->
                val backgroundColor = when (state.isError) {
                    true -> MaterialTheme.colorScheme.error
                    false -> MaterialTheme.colorScheme.primary
                }
                Snackbar(
                    snackbarData = data,
                    containerColor = backgroundColor,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
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
                            Screen.PersonalInformation -> {
                                PersonalInformation(
                                    firstName = state.firstName,
                                    lastName = state.lastName,
                                    middleName = state.middleName,
                                    maidenName = state.maidenName,
                                    firstNameChange = { action(NewMemberAction.FirstNameChange(it)) },
                                    lastNameChange = { action(NewMemberAction.LastNameChange(it)) },
                                    middleNameChange = { action(NewMemberAction.MiddleNameChange(it)) },
                                    maidenNameChange = { action(NewMemberAction.MaidenNameChange(it)) },
                                    next = { action(NewMemberAction.ScreenChange(Screen.OptionalInformation)) }
                                )
                            }

                            Screen.OptionalInformation -> {
                                OptionalInformation(
                                    birthday = state.birthday,
                                    gender = state.gender,
                                    role = state.role,
                                    birthdayChange = { action(NewMemberAction.BirthdayChange(it)) },
                                    genderChange = { action(NewMemberAction.GenderChange(it)) },
                                    roleChange = { action(NewMemberAction.RoleChange(it)) },
                                    prev = { action(NewMemberAction.ScreenChange(Screen.PersonalInformation)) },
                                    next = { action(NewMemberAction.ScreenChange(Screen.AccountInformation)) }
                                )
                            }

                            Screen.AccountInformation -> {
                                AccountInformation(
                                    email = state.email,
                                    password = state.password,
                                    emailChange = { action(NewMemberAction.EmailChange(it)) },
                                    passwordChange = { action(NewMemberAction.PasswordChange(it)) },
                                    prev = { action(NewMemberAction.ScreenChange(Screen.OptionalInformation)) },
                                    next = { action(NewMemberAction.ScreenChange(Screen.Summary)) }
                                )
                            }

                            Screen.Summary -> {
                                Summary(
                                    firstName = state.firstName,
                                    lastName = state.lastName,
                                    middleName = state.middleName,
                                    maidenName = state.maidenName,
                                    gender = state.gender.name,
                                    role = state.role.name,
                                    email = state.email,
                                    register = { action(NewMemberAction.Register) },
                                    prev = { action(NewMemberAction.ScreenChange(Screen.OptionalInformation)) }
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
private fun PersonalInformation(
    firstName: String,
    lastName: String,
    middleName: String,
    maidenName: String,
    firstNameChange: (String) -> Unit,
    lastNameChange: (String) -> Unit,
    middleNameChange: (String) -> Unit,
    maidenNameChange: (String) -> Unit,
    next: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Basic Information",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Enter the member's basic information.",
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
        placeHolderText = "Member's First Name"
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
        placeHolderText = "Member's Last Name"
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
        placeHolderText = "Member's Middle Name"
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
        labelText = "Member's Maiden Name",
        placeHolderText = "(Optional)"
    )

    PrimaryButton(
        onClick = { next() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Next"
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
        text = "Additional Details",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Add optional information to complete the member's profile.",
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

    DropDownSelector(
        label = "Role",
        options = FamilyRole.entries.toList(),
        selectedOption = role,
        onOptionSelected = roleChange,
        leadingIcon = Icons.Outlined.Security,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    DropDownSelector(
        label = "Gender",
        options = Gender.entries.toList(),
        selectedOption = gender,
        onOptionSelected = genderChange,
        leadingIcon = Icons.Outlined.Male,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))
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
        text = "Account Setup",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Set login credentials for this member.",
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
        placeHolderText = "newmember@example.com"
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
        placeHolderText = "Member's Password"
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
    firstName: String,
    lastName: String,
    middleName: String,
    maidenName: String,
    gender: String,
    role: String,
    email: String,
    register: () -> Unit,
    prev: () -> Unit
) {
    Text(
        text = "Review Member Details",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Confirm the information before adding this member.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(16.dp))
    SummaryComponent(
        label = "First Name",
        value = firstName
    )
    SummaryComponent(
        label = "Last Name",
        value = lastName
    )
    SummaryComponent(
        label = "Middle Name",
        value = middleName
    )
    SummaryComponent(
        label = "Maiden Name",
        value = maidenName
    )
    SummaryComponent(
        label = "Role",
        value = role
    )
    SummaryComponent(
        label = "Gender",
        value = gender
    )
    SummaryComponent(
        label = "Email",
        value = email
    )

    PrimaryButton(
        onClick = { register() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        defaultText = "Add Member"
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
private fun SummaryComponent(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}