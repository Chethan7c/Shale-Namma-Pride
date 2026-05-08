package com.shalenamma.pride.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.shalenamma.pride.data.models.UserRole
import com.shalenamma.pride.ui.components.*
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.Spacing
import com.shalenamma.pride.ui.viewmodels.MealViewModel
import com.shalenamma.pride.ui.viewmodels.UserSessionViewModel
import com.shalenamma.pride.ui.viewmodels.LanguageViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.res.stringResource
import com.shalenamma.pride.R
import kotlinx.coroutines.flow.map
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsScreen(navController: NavHostController) {
    val sessionViewModel: UserSessionViewModel = hiltViewModel()
    val mealViewModel: MealViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = hiltViewModel()

    val userRole by sessionViewModel.userRole.collectAsState()
    val uiState by mealViewModel.uiState.collectAsState()
    val currentLang by languageViewModel.languagePreferenceManager.languageFlow.collectAsState(initial = "en")
    val isKannada = currentLang == "kn"

    LaunchedEffect(currentLang) {
        Log.d("MealsScreen", "Language changed to: $currentLang, isKannada: $isKannada")
    }

    val showAddDialog = remember { mutableStateOf(false) }
    val mealName = remember { mutableStateOf("") }
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val selectedImageUrl = remember { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        Log.d("MealsScreen", "Image picker result: $uri")
        if (uri != null) {
            Log.d("MealsScreen", "Image selected: $uri")
            selectedImageUri.value = uri
        } else {
            Log.d("MealsScreen", "No image selected")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.meals)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.White)
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = "meals")
        },
        floatingActionButton = {
            if (userRole != UserRole.PARENT) {
                FloatingActionButton(
                    onClick = { showAddDialog.value = true },
                    containerColor = Colors.TrustBlue
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Meal")
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
                SectionHeader(text = stringResource(R.string.today_meal))
            }

            if (uiState.todaysMeal != null) {
                item {
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
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "🍽️ ${if (isKannada && uiState.todaysMeal!!.titleKn.isNotEmpty()) uiState.todaysMeal!!.titleKn else uiState.todaysMeal!!.title}",
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                }
                                if (userRole != UserRole.PARENT) {
                                    IconButton(
                                        onClick = { mealViewModel.deleteMeal(uiState.todaysMeal!!.id) },
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

                            if (uiState.todaysMeal!!.imageUrl.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(androidx.compose.ui.graphics.Color.LightGray)
                                        .clickable { selectedImageUrl.value = uiState.todaysMeal!!.imageUrl },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            model = uiState.todaysMeal!!.imageUrl,
                                            contentScale = ContentScale.Crop
                                        ),
                                        contentDescription = "Meal image",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            } else {
                                Text(
                                    text = "No image URL saved",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Colors.MediumGray
                                )
                            }
                        }
                    }
                }
            } else {
                item {
                    Text(
                        text = stringResource(R.string.todays_meal_not_updated),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Colors.MediumGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                SectionHeader(text = stringResource(R.string.previous_meals))
            }

            if (uiState.meals.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.no_previous_meals),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Colors.MediumGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(uiState.meals) { meal ->
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
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "🍽️ ${if (isKannada && meal.titleKn.isNotEmpty()) meal.titleKn else meal.title}",
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                    Text(
                                        text = formatMealDate(meal.date),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Colors.MediumGray,
                                        modifier = Modifier.padding(top = Spacing.xs)
                                    )
                                }
                                if (userRole != UserRole.PARENT) {
                                    IconButton(
                                        onClick = { mealViewModel.deleteMeal(meal.id) },
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

                            if (meal.imageUrl.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(androidx.compose.ui.graphics.Color.LightGray)
                                        .clickable { selectedImageUrl.value = meal.imageUrl },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            model = meal.imageUrl,
                                            contentScale = ContentScale.Crop
                                        ),
                                        contentDescription = "Meal image",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(Spacing.lg))
            }
        }
    }

    if (selectedImageUrl.value != null) {
        FullScreenImageViewer(
            imageUrl = selectedImageUrl.value!!,
            onDismiss = { selectedImageUrl.value = null }
        )
    }

    if (showAddDialog.value && userRole != UserRole.PARENT) {
        AlertDialog(
            onDismissRequest = { showAddDialog.value = false },
            title = { Text(stringResource(R.string.add_today_meal)) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(Spacing.md)
                ) {
                    if (uiState.error != null) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = Colors.WarningOrange,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    if (uiState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }

                    ShaleNammaTextField(
                        value = mealName.value,
                        onValueChange = { mealName.value = it },
                        label = stringResource(R.string.meal_name),
                        placeholder = "e.g., Biryani, Sambar Rice"
                    )

                    if (selectedImageUri.value != null) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri.value),
                            contentDescription = "Selected meal image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Button(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("📷 Add Image")
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (mealName.value.isNotEmpty()) {
                            Log.d("MealsScreen", "Add button clicked: name=${mealName.value}, imageUri=${selectedImageUri.value}")
                            mealViewModel.addTodaysMeal(
                                name = mealName.value,
                                description = "",
                                imageUri = selectedImageUri.value
                            )
                            mealName.value = ""
                            selectedImageUri.value = null
                            showAddDialog.value = false
                            mealViewModel.clearError()
                        }
                    },
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text(stringResource(R.string.add))
                    }
                }
            },
            dismissButton = {
                Button(onClick = {
                    showAddDialog.value = false
                    selectedImageUri.value = null
                    mealViewModel.clearError()
                }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
private fun rememberScrollState(): ScrollState {
    return rememberScrollState()
}

private fun formatMealDate(date: Date): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(date)
}
