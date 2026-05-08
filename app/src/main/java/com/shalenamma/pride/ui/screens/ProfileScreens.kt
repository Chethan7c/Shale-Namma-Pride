package com.shalenamma.pride.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shalenamma.pride.data.models.UserRole
import com.shalenamma.pride.ui.components.*
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.Spacing
import com.shalenamma.pride.ui.viewmodels.AuthViewModel
import com.shalenamma.pride.ui.viewmodels.LanguagePreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.ui.res.stringResource
import com.shalenamma.pride.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, authViewModel: AuthViewModel = hiltViewModel()) {
    val uiState by authViewModel.uiState.collectAsState()
    val user = uiState.user

    if (user != null) {
        when (user.role) {
            UserRole.ADMIN -> AdminProfileScreen(navController, authViewModel, user)
            UserRole.TEACHER -> TeacherProfileScreen(navController, authViewModel, user)
            else -> ParentProfileScreen(navController, authViewModel, user)
        }
    } else {
        ParentProfileScreen(navController, authViewModel, null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminProfileScreen(navController: NavHostController, authViewModel: AuthViewModel, user: com.shalenamma.pride.data.models.User) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.White)
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = "profile")
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
                ShaleNammaCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                    ) {
                        Text(
                            text = "👤",
                            style = MaterialTheme.typography.displayLarge
                        )
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        ShaleNammaBadge(text = "ADMIN")
                    }
                }
            }

            item {
                SectionHeader(text = stringResource(R.string.account_information))
            }

            item {
                ShaleNammaCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(stringResource(R.string.email), style = MaterialTheme.typography.bodyMedium)
                            Text(user.email, style = MaterialTheme.typography.bodyMedium, color = Colors.MediumGray)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(stringResource(R.string.role), style = MaterialTheme.typography.bodyMedium)
                            Text(stringResource(R.string.admin), style = MaterialTheme.typography.bodyMedium, color = Colors.MediumGray)
                        }
                    }
                }
            }

            item {
                SectionHeader(text = stringResource(R.string.actions))
            }

            item {
                PrimaryButton(
                    text = stringResource(R.string.sign_out),
                    onClick = {
                        authViewModel.signOut()
                        navController.navigate("role_select") {
                            popUpTo("profile") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                PrimaryButton(
                    text = stringResource(R.string.view_feedback),
                    onClick = { navController.navigate("feedback_list") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                LanguageToggle()
            }

            item {
                Spacer(modifier = Modifier.height(Spacing.lg))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherProfileScreen(navController: NavHostController, authViewModel: AuthViewModel, user: com.shalenamma.pride.data.models.User) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.White)
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = "profile")
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
                ShaleNammaCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                    ) {
                        Text(
                            text = "👨‍🏫",
                            style = MaterialTheme.typography.displayLarge
                        )
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        ShaleNammaBadge(text = "TEACHER")
                    }
                }
            }

            item {
                SectionHeader(text = stringResource(R.string.account_information))
            }

            item {
                ShaleNammaCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(stringResource(R.string.email), style = MaterialTheme.typography.bodyMedium)
                            Text(user.email, style = MaterialTheme.typography.bodyMedium, color = Colors.MediumGray)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(stringResource(R.string.role), style = MaterialTheme.typography.bodyMedium)
                            Text(stringResource(R.string.teacher), style = MaterialTheme.typography.bodyMedium, color = Colors.MediumGray)
                        }
                    }
                }
            }

            item {
                SectionHeader(text = stringResource(R.string.actions))
            }

            item {
                PrimaryButton(
                    text = stringResource(R.string.sign_out),
                    onClick = {
                        authViewModel.signOut()
                        navController.navigate("role_select") {
                            popUpTo("profile") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                PrimaryButton(
                    text = stringResource(R.string.view_feedback),
                    onClick = { navController.navigate("feedback_list") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                LanguageToggle()
            }

            item {
                Spacer(modifier = Modifier.height(Spacing.lg))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentProfileScreen(navController: NavHostController, authViewModel: AuthViewModel, user: com.shalenamma.pride.data.models.User?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.White)
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = "profile")
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
                ShaleNammaCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                    ) {
                        Text(
                            text = "👨‍👩‍👧",
                            style = MaterialTheme.typography.displayLarge
                        )
                        Text(
                            text = user?.name ?: "Parent",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        ShaleNammaBadge(text = "PARENT")
                    }
                }
            }

            item {
                SectionHeader(text = stringResource(R.string.actions))
            }

            item {
                PrimaryButton(
                    text = stringResource(R.string.send_feedback),
                    onClick = { navController.navigate("feedback") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (user != null) {
                item {
                    SectionHeader(text = stringResource(R.string.account_information))
                }

                item {
                    ShaleNammaCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Spacing.md),
                            verticalArrangement = Arrangement.spacedBy(Spacing.md)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(stringResource(R.string.email), style = MaterialTheme.typography.bodyMedium)
                                Text(user.email, style = MaterialTheme.typography.bodyMedium, color = Colors.MediumGray)
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(stringResource(R.string.role), style = MaterialTheme.typography.bodyMedium)
                                Text(stringResource(R.string.parent), style = MaterialTheme.typography.bodyMedium, color = Colors.MediumGray)
                            }
                        }
                    }
                }

                item {
                    PrimaryButton(
                        text = stringResource(R.string.sign_out),
                        onClick = {
                            authViewModel.signOut()
                            navController.navigate("role_select") {
                                popUpTo("profile") { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                LanguageToggle()
            }

            item {
                Spacer(modifier = Modifier.height(Spacing.lg))
            }
        }
    }
}
