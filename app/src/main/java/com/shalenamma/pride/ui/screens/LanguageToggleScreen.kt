package com.shalenamma.pride.ui.screens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.shalenamma.pride.ui.components.ShaleNammaCard
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.Spacing
import com.shalenamma.pride.ui.viewmodels.LanguageViewModel
import com.shalenamma.pride.ui.viewmodels.AnnouncementViewModel
import com.shalenamma.pride.ui.viewmodels.AchievementViewModel
import com.shalenamma.pride.ui.viewmodels.MealViewModel
import androidx.compose.ui.res.stringResource
import com.shalenamma.pride.R

@Composable
fun LanguageToggle(languageViewModel: LanguageViewModel = hiltViewModel()) {
    val currentLang by languageViewModel.languagePreferenceManager.languageFlow.collectAsState(initial = "en")
    val context = LocalContext.current
    val announcementViewModel: AnnouncementViewModel = hiltViewModel()
    val achievementViewModel: AchievementViewModel = hiltViewModel()
    val mealViewModel: MealViewModel = hiltViewModel()

    ShaleNammaCard {
        Column(
            modifier = Modifier.fillMaxWidth().padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            Text(stringResource(R.string.language_toggle), style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        Log.d("LanguageToggle", "Switching to English")
                        languageViewModel.languagePreferenceManager.setLanguage("en")
                        (context as? Activity)?.recreate()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentLang == "en") Colors.TrustBlue else Colors.LightGray
                    )
                ) {
                    Text(stringResource(R.string.english))
                }
                Button(
                    onClick = {
                        Log.d("LanguageToggle", "Switching to Kannada")
                        languageViewModel.languagePreferenceManager.setLanguage("kn")
                        (context as? Activity)?.recreate()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentLang == "kn") Colors.TrustBlue else Colors.LightGray
                    )
                ) {
                    Text(stringResource(R.string.kannada))
                }
            }
        }
    }
}
