// Shale-Namma Pride - Splash Screen
// File: app/src/main/java/com/shalenamma/pride/ui/screens/SplashScreen.kt

package com.shalenamma.pride.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shalenamma.pride.data.models.UserRole
import com.shalenamma.pride.ui.components.ShaleNammaLoadingIndicator
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.Spacing
import com.shalenamma.pride.ui.viewmodels.AuthViewModel
import com.shalenamma.pride.ui.viewmodels.UserSessionViewModel
import androidx.compose.ui.res.stringResource
import com.shalenamma.pride.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    sessionViewModel: UserSessionViewModel = hiltViewModel()
) {
    val uiState = authViewModel.uiState.collectAsState().value
    val userRole = sessionViewModel.userRole.collectAsState().value

    LaunchedEffect(uiState.user) {
        delay(2000)
        val dest = if (uiState.user != null) "home" else "role_select"
        Log.d("SplashScreen", "Navigating to: $dest (user=${uiState.user?.email})")
        navController.navigate(dest) {
            popUpTo("splash") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo/Icon
        Text(
            text = "🏫",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = Spacing.lg)
        )

        // App Name
        Text(
            text = stringResource(R.string.shale_namma_pride),
            style = MaterialTheme.typography.displayMedium,
            color = Colors.TrustBlue,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md)
                .padding(bottom = Spacing.sm)
        )

        // Subtitle
        Text(
            text = stringResource(R.string.splash_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = Colors.MediumGray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md)
                .padding(bottom = Spacing.xl)
        )

        // Loading Indicator
        ShaleNammaLoadingIndicator()
    }
}