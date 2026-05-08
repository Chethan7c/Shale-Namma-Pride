package com.shalenamma.pride.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shalenamma.pride.data.models.UserRole
import com.shalenamma.pride.ui.components.*
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.Spacing
import com.shalenamma.pride.ui.viewmodels.AnnouncementViewModel
import android.util.Log
import com.shalenamma.pride.ui.viewmodels.AuthViewModel
import com.shalenamma.pride.ui.viewmodels.UserSessionViewModel
import com.shalenamma.pride.ui.viewmodels.LanguageViewModel
import androidx.compose.ui.res.stringResource
import com.shalenamma.pride.R
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val sessionViewModel: UserSessionViewModel = hiltViewModel()
    val announcementViewModel: AnnouncementViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = hiltViewModel()

    val userRole by sessionViewModel.userRole.collectAsState()
    val announcements by announcementViewModel.uiState.collectAsState()
    val authState by authViewModel.uiState.collectAsState()
    val currentLang by languageViewModel.languagePreferenceManager.languageFlow.collectAsState(initial = "en")
    val isKannada = currentLang == "kn"

    LaunchedEffect(currentLang) {
        Log.d("HomeScreen", "Language changed to: $currentLang, isKannada: $isKannada")
    }

    val showAddDialog = remember { mutableStateOf(false) }
    val announcementTitle = remember { mutableStateOf("") }
    val announcementContent = remember { mutableStateOf("") }
    val announcementTitleKn = remember { mutableStateOf("") }
    val announcementContentKn = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.White)
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = "home")
        },
        floatingActionButton = {
            if (userRole != UserRole.PARENT) {
                FloatingActionButton(
                    onClick = { showAddDialog.value = true },
                    containerColor = Colors.TrustBlue
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Announcement")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            item {
                SectionHeader(text = stringResource(R.string.announcements))
            }

            if (announcements.announcements.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.no_announcements),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Colors.MediumGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(announcements.announcements) { announcement ->
                    ShaleNammaCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Spacing.md),
                            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isKannada && announcement.titleKn.isNotEmpty()) announcement.titleKn else announcement.title,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.weight(1f)
                                )
                                if (userRole != UserRole.PARENT) {
                                    IconButton(
                                        onClick = { announcementViewModel.deleteAnnouncement(announcement.id) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Colors.WarningOrange
                                        )
                                    }
                                }
                            }
                            Text(
                                text = if (isKannada && announcement.contentKn.isNotEmpty()) announcement.contentKn else announcement.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Colors.MediumGray
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(Spacing.lg))
            }
        }
    }

    if (showAddDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showAddDialog.value = false
                announcementTitleKn.value = ""
                announcementContentKn.value = ""
            },
            title = { Text(stringResource(R.string.add_announcement)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    ShaleNammaTextField(
                        value = announcementTitle.value,
                        onValueChange = { announcementTitle.value = it },
                        label = stringResource(R.string.announcement_title),
                        placeholder = stringResource(R.string.announcement_title)
                    )
                    ShaleNammaTextField(
                        value = announcementContent.value,
                        onValueChange = { announcementContent.value = it },
                        label = stringResource(R.string.announcement_content),
                        placeholder = stringResource(R.string.announcement_content),
                        maxLines = 5,
                        minLines = 3,
                        singleLine = false
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (announcementTitle.value.isNotEmpty() && announcementContent.value.isNotEmpty()) {
                            announcementViewModel.addAnnouncement(
                                title = announcementTitle.value,
                                content = announcementContent.value,
                                createdBy = authState.user?.email ?: "Unknown",
                                titleKn = announcementTitleKn.value,
                                contentKn = announcementContentKn.value
                            )
                            announcementTitle.value = ""
                            announcementContent.value = ""
                            announcementTitleKn.value = ""
                            announcementContentKn.value = ""
                            showAddDialog.value = false
                        }
                    }
                ) {
                    Text(stringResource(R.string.add))
                }
            },
            dismissButton = {
                Button(onClick = {
                    showAddDialog.value = false
                    announcementTitleKn.value = ""
                    announcementContentKn.value = ""
                }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}
