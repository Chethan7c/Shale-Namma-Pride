package com.shalenamma.pride.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.Spacing
import com.shalenamma.pride.ui.viewmodels.RoleSelectViewModel
import androidx.compose.ui.res.stringResource
import com.shalenamma.pride.R

@Composable
fun RoleSelectScreen(
    navController: NavHostController,
    roleSelectViewModel: RoleSelectViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(Spacing.md),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🏫",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = Spacing.md)
        )
        Text(
            text = stringResource(R.string.shale_namma_pride),
            style = MaterialTheme.typography.headlineMedium,
            color = Colors.TrustBlue,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.who_are_you),
            style = MaterialTheme.typography.bodyLarge,
            color = Colors.MediumGray,
            modifier = Modifier.padding(top = Spacing.sm, bottom = Spacing.xl)
        )

        Button(
            onClick = {
                try {
                    Log.d("RoleSelectScreen", "Teacher button clicked")
                    roleSelectViewModel.selectTeacher()
                    Log.d("RoleSelectScreen", "Teacher role set, navigating to login")
                    navController.navigate("login")
                } catch (e: Exception) {
                    Log.e("RoleSelectScreen", "Error selecting teacher role: ${e.message}", e)
                }
            },
            modifier = Modifier.fillMaxWidth().height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Colors.TrustBlue)
        ) {
            Text(stringResource(R.string.im_teacher), style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(Spacing.md))

        OutlinedButton(
            onClick = {
                try {
                    Log.d("RoleSelectScreen", "Parent button clicked")
                    roleSelectViewModel.selectParent()
                    Log.d("RoleSelectScreen", "Parent role set, navigating to home")
                    navController.navigate("home") {
                        popUpTo("role_select") { inclusive = true }
                    }
                } catch (e: Exception) {
                    Log.e("RoleSelectScreen", "Error selecting parent role: ${e.message}", e)
                }
            },
            modifier = Modifier.fillMaxWidth().height(64.dp)
        ) {
            Text(stringResource(R.string.im_parent), style = MaterialTheme.typography.titleMedium)
        }
    }
}
