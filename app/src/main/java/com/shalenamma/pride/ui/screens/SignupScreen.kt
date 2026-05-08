// Shale-Namma Pride - Signup Screen
// File: app/src/main/java/com/shalenamma/pride/ui/screens/SignupScreen.kt

package com.shalenamma.pride.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shalenamma.pride.ui.components.*
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.Spacing
import com.shalenamma.pride.ui.theme.LayoutDimensions
import com.shalenamma.pride.ui.viewmodels.AuthViewModel
import androidx.compose.ui.res.stringResource
import com.shalenamma.pride.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavHostController, authViewModel: AuthViewModel = hiltViewModel()) {
    val fullName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val school = remember { mutableStateOf("") }
    val role = remember { mutableStateOf("Admin") }
    val password = remember { mutableStateOf("") }
    val agreeTerms = remember { mutableStateOf(false) }
    val uiState by authViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate("home") { popUpTo("signup") { inclusive = true } }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text(stringResource(R.string.sign_up)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Colors.White
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            Text(
                text = "Admin or Teacher account required for posting",
                style = MaterialTheme.typography.bodyMedium,
                color = Colors.MediumGray
            )

            // Full Name
            ShaleNammaTextField(
                value = fullName.value,
                onValueChange = { fullName.value = it },
                label = "Full Name",
                placeholder = "Enter your full name",
                keyboardType = KeyboardType.Text
            )

            // Email
            EmailInputField(
                value = email.value,
                onValueChange = { email.value = it }
            )

            // Phone
            ShaleNammaTextField(
                value = phone.value,
                onValueChange = { phone.value = it },
                label = "Phone Number",
                placeholder = "+91 XXXXX XXXXX",
                keyboardType = KeyboardType.Phone
            )

            // School Name
            ShaleNammaTextField(
                value = school.value,
                onValueChange = { school.value = it },
                label = "School Name",
                placeholder = "Enter school name"
            )

            // Role Dropdown (Simple TextField for now)
            ShaleNammaTextField(
                value = role.value,
                onValueChange = { role.value = it },
                label = "Role",
                placeholder = "Admin or Teacher"
            )

            // Password
            PasswordInputField(
                value = password.value,
                onValueChange = { password.value = it },
                placeholder = "Min 8 characters, 1 uppercase, 1 number"
            )

            // Terms Checkbox
            CheckboxWithLabel(
                checked = agreeTerms.value,
                onCheckedChange = { agreeTerms.value = it },
                label = "I agree to Terms & Privacy Policy"
            )

            // Create Account Button
            PrimaryButton(
                text = "Create Account",
                onClick = { authViewModel.signUp(email.value, password.value, fullName.value) },
                isLoading = uiState.isLoading,
                enabled = fullName.value.isNotEmpty() &&
                         email.value.isNotEmpty() &&
                         phone.value.isNotEmpty() &&
                         password.value.isNotEmpty() &&
                         agreeTerms.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.lg)
            )

            Spacer(modifier = Modifier.height(Spacing.lg))
        }
    }
}
