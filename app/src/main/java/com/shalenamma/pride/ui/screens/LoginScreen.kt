// Shale-Namma Pride - Login Screen
// File: app/src/main/java/com/shalenamma/pride/ui/screens/LoginScreen.kt

package com.shalenamma.pride.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shalenamma.pride.ui.components.*
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.Spacing
import com.shalenamma.pride.ui.viewmodels.AuthViewModel

@Composable
fun LoginScreen(navController: NavHostController, authViewModel: AuthViewModel = hiltViewModel()) {
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val rememberMe = remember { mutableStateOf(false) }
    val uiState by authViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate("home") { popUpTo("login") { inclusive = true } }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Spacing.md),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Shale-Namma Pride",
            style = MaterialTheme.typography.displayMedium,
            color = Colors.TrustBlue,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.sm)
        )

        // Subtitle
        Text(
            text = "School Transparency Portal",
            style = MaterialTheme.typography.bodyMedium,
            color = Colors.MediumGray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.lg)
        )

        // Email Input
        EmailInputField(
            value = emailValue.value,
            onValueChange = { emailValue.value = it },
            modifier = Modifier.fillMaxWidth()
        )

        // Password Input
        PasswordInputField(
            value = passwordValue.value,
            onValueChange = { passwordValue.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing.md)
        )

        // Remember Me
        CheckboxWithLabel(
            checked = rememberMe.value,
            onCheckedChange = { rememberMe.value = it },
            label = "Remember Me",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = Spacing.md)
        )

        // Error message
        if (uiState.error != null) {
            Text(
                text = uiState.error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = Spacing.sm)
            )
        }

        // Sign In Button
        PrimaryButton(
            text = "Sign In",
            onClick = { authViewModel.login(emailValue.value, passwordValue.value) },
            isLoading = uiState.isLoading,
            enabled = emailValue.value.isNotEmpty() && passwordValue.value.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing.lg)
        )

        // Back to role select
        TextButtonComponent(
            text = "Back",
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(top = Spacing.md)
        )
    }
}
