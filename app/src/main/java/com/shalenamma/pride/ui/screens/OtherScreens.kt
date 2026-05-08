package com.shalenamma.pride.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.clickable
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shalenamma.pride.R
import com.shalenamma.pride.data.models.UserRole
import com.shalenamma.pride.ui.components.*
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.Spacing
import android.util.Log
import com.shalenamma.pride.ui.viewmodels.*
import kotlinx.coroutines.delay
import android.net.Uri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(
    navController: NavHostController,
    achievementViewModel: AchievementViewModel = hiltViewModel(),
    sessionViewModel: UserSessionViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val uiState by achievementViewModel.uiState.collectAsState()
    val userRole by sessionViewModel.userRole.collectAsState()
    val userId by sessionViewModel.userId.collectAsState()
    val currentLang by languageViewModel.languagePreferenceManager.languageFlow.collectAsState()
    val isKannada = currentLang == "kn"

    LaunchedEffect(currentLang) {
        Log.d("AchievementsScreen", "Language changed to: $currentLang, isKannada: $isKannada")
    }
    val showEditDialog = remember { mutableStateOf(false) }
    val editingAchievementId = remember { mutableStateOf<String?>(null) }
    val studentName = remember { mutableStateOf("") }
    val achievementTitle = remember { mutableStateOf("") }
    val showTranslateDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.student_achievements)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (isKannada && userRole != UserRole.PARENT) {
                        IconButton(
                            onClick = { showTranslateDialog.value = true },
                            enabled = !uiState.isLoading
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                            } else {
                                Icon(Icons.Default.Translate, contentDescription = "Translate All")
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.White)
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = "achievements")
        },
        floatingActionButton = {}
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            if (uiState.error != null) {
                item {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = Colors.WarningOrange,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth().padding(Spacing.md)
                    )
                }
            }

            items(uiState.achievements) { achievement ->
                ShaleNammaCard {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                    ) {
                        ShaleNammaBadge(text = achievement.category.name, backgroundColor = Colors.WarningOrange)
                        // Show edit option on TOP for achievement with "c" in name
                        val isTargetAchievement = achievement.studentName.contains("c", ignoreCase = true) ||
                                achievement.title.contains("c", ignoreCase = true)
                        if (isTargetAchievement && userRole != UserRole.PARENT) {
                            SecondaryButton(
                                text = "Edit Achievement",
                                onClick = {
                                    editingAchievementId.value = achievement.id
                                    studentName.value = achievement.studentName
                                    achievementTitle.value = achievement.title
                                    showEditDialog.value = true
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Text(
                            text = "${if (isKannada && achievement.studentNameKn.isNotEmpty()) achievement.studentNameKn else achievement.studentName} - ${if (isKannada && achievement.titleKn.isNotEmpty()) achievement.titleKn else achievement.title}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = if (isKannada && achievement.descriptionKn.isNotEmpty()) achievement.descriptionKn else achievement.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                        ) {
                            val hasLiked = achievement.likedBy.contains(userId)

                            if (userRole != UserRole.PARENT && !isTargetAchievement) {
                                SecondaryButton(
                                    text = "Edit",
                                    onClick = {
                                        editingAchievementId.value = achievement.id
                                        studentName.value = achievement.studentName
                                        showEditDialog.value = true
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            SecondaryButton(
                                text = if (hasLiked) "Liked: ${achievement.likes}" else "Like: ${achievement.likes}",
                                onClick = {
                                    if (!hasLiked && userId.isNotEmpty()) {
                                        achievementViewModel.likeAchievement(achievement.id, userId)
                                    }
                                },
                                enabled = !hasLiked && userId.isNotEmpty(),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(Spacing.lg)) }
        }
    }

    if (showEditDialog.value && userRole != UserRole.PARENT) {
        AlertDialog(
            onDismissRequest = { showEditDialog.value = false },
            title = { Text(stringResource(R.string.edit_achievement)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    ShaleNammaTextField(
                        value = studentName.value,
                        onValueChange = { studentName.value = it },
                        label = "Student Name",
                        placeholder = "e.g., Raj Kumar"
                    )
                    ShaleNammaTextField(
                        value = achievementTitle.value,
                        onValueChange = { achievementTitle.value = it },
                        label = "Achievement Title",
                        placeholder = "e.g., Science Fair Winner"
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (studentName.value.isNotEmpty() && editingAchievementId.value != null) {
                            achievementViewModel.updateAchievement(
                                achievementId = editingAchievementId.value!!,
                                studentName = studentName.value,
                                title = achievementTitle.value
                            )
                            studentName.value = ""
                            achievementTitle.value = ""
                            editingAchievementId.value = null
                            showEditDialog.value = false
                        }
                    }
                ) {
                    Text(stringResource(R.string.update))
                }
            },
            dismissButton = {
                Button(onClick = {
                    showEditDialog.value = false
                    studentName.value = ""
                    achievementTitle.value = ""
                    editingAchievementId.value = null
                }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    if (showTranslateDialog.value) {
        AlertDialog(
            onDismissRequest = { showTranslateDialog.value = false },
            title = { Text("Translate All to Kannada") },
            text = { Text("This will translate all achievement titles, descriptions, and student names to Kannada using AI. This may take a few minutes. Continue?") },
            confirmButton = {
                Button(
                    onClick = {
                        showTranslateDialog.value = false
                        achievementViewModel.bulkTranslateAll()
                    },
                    enabled = !uiState.isLoading
                ) {
                    Text("Translate")
                }
            },
            dismissButton = {
                Button(onClick = { showTranslateDialog.value = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    navController: NavHostController,
    galleryViewModel: GalleryViewModel = hiltViewModel(),
    sessionViewModel: UserSessionViewModel = hiltViewModel()
) {
    val uiState by galleryViewModel.uiState.collectAsState()
    val allImages = uiState.galleries.flatMap { gallery -> gallery.images }
    val userRole by sessionViewModel.userRole.collectAsState()
    val isAdminOrTeacher = userRole == UserRole.ADMIN || userRole == UserRole.TEACHER

    var showDeleteDialog by remember { mutableStateOf(false) }
    var imageToDelete by remember { mutableStateOf<com.shalenamma.pride.data.models.GalleryImage?>(null) }

    val multipleImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (uris.isNotEmpty()) galleryViewModel.addImagesDirectly(uris)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (isAdminOrTeacher) {
                        IconButton(onClick = { multipleImagePicker.launch("image/*") }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Images")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.White)
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController, currentRoute = "gallery") }
    ) { paddingValues ->
        if (allImages.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("No images yet", style = MaterialTheme.typography.bodyMedium, color = Colors.MediumGray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(Spacing.xs),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                val rows = allImages.chunked(2)
                itemsIndexed(rows) { rowIndex, rowImages ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                        rowImages.forEachIndexed { colIndex, image ->
                            val imageIndex = rowIndex * 2 + colIndex
                            Box(modifier = Modifier.weight(1f)) {
                                AsyncImage(
                                    model = image.url, contentDescription = null,
                                    modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                                        .clip(MaterialTheme.shapes.small)
                                        .clickable { navController.navigate("image_detail/$imageIndex") },
                                    contentScale = ContentScale.Crop
                                )
                                if (isAdminOrTeacher) {
                                    IconButton(
                                        onClick = { imageToDelete = image; showDeleteDialog = true },
                                        modifier = Modifier.align(Alignment.TopEnd).size(32.dp).padding(4.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Colors.ErrorRed, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                        repeat(2 - rowImages.size) { Spacer(modifier = Modifier.weight(1f).aspectRatio(1f)) }
                    }
                }
                item { Spacer(modifier = Modifier.height(Spacing.lg)) }
            }
        }
    }

    if (showDeleteDialog && imageToDelete != null && isAdminOrTeacher) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false; imageToDelete = null },
            title = { Text("Delete Image") },
            text = { Text("Are you sure you want to delete this image? This action cannot be undone.") },
            confirmButton = {
                Button(onClick = { imageToDelete?.let { galleryViewModel.deleteGalleryImage(it.url) }; showDeleteDialog = false; imageToDelete = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Colors.ErrorRed)) { Text("Delete") }
            },
            dismissButton = { OutlinedButton(onClick = { showDeleteDialog = false; imageToDelete = null }) { Text("Cancel") } }
        )
    }
}

@Composable
fun GalleryAddScreen(navController: NavHostController, galleryViewModel: GalleryViewModel = hiltViewModel()) {
    var title by remember { mutableStateOf("") }
    val imageUris = remember { mutableStateListOf<Uri>() }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { imageUris.add(it) }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(Spacing.lg),
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text("Add Gallery Images", style = MaterialTheme.typography.headlineSmall)
        ShaleNammaTextField(
            value = title,
            onValueChange = { title = it },
            label = "Gallery Title",
            placeholder = "e.g., Science Lab"
        )
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Image (${imageUris.size} selected)")
        }
        if (imageUris.isNotEmpty()) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                items(imageUris) { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp).clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Button(
            onClick = {
                if (title.isNotBlank() && imageUris.isNotEmpty()) {
                    galleryViewModel.addGalleryImages(title, imageUris.toList())
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = title.isNotBlank() && imageUris.isNotEmpty()
        ) {
            Text("Upload ${imageUris.size} Images")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryDetailScreen(
    navController: NavHostController,
    imageIndex: Int,
    galleryViewModel: GalleryViewModel = hiltViewModel(),
    sessionViewModel: UserSessionViewModel = hiltViewModel()
) {
    val uiState by galleryViewModel.uiState.collectAsState()
    val allImages = uiState.galleries.flatMap { gallery -> gallery.images }
    var currentIndex by remember { mutableStateOf(imageIndex) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val userRole by sessionViewModel.userRole.collectAsState()
    val isAdminOrTeacher = userRole == UserRole.ADMIN || userRole == UserRole.TEACHER

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${currentIndex + 1} / ${allImages.size}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (isAdminOrTeacher) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Colors.ErrorRed)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.White)
            )
        }
    ) { paddingValues ->
        if (allImages.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("No images yet", color = Colors.MediumGray)
            }
        } else if (currentIndex in allImages.indices) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
                    .pointerInput(Unit) {
                        var dragAccumulator = 0f
                        detectHorizontalDragGestures(
                            onDragStart = { dragAccumulator = 0f },
                            onHorizontalDrag = { _, dragAmount -> dragAccumulator += dragAmount },
                            onDragEnd = {
                                if (dragAccumulator > 100 && currentIndex > 0) currentIndex--
                                else if (dragAccumulator < -100 && currentIndex < allImages.size - 1) currentIndex++
                            }
                        )
                    }
            ) {
                AsyncImage(model = allImages[currentIndex].url, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Fit)
            }
        }
    }

    if (showDeleteDialog && currentIndex in allImages.indices && isAdminOrTeacher) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Image") },
            text = { Text("Are you sure you want to delete this image? This action cannot be undone.") },
            confirmButton = {
                Button(onClick = {
                    galleryViewModel.deleteGalleryImage(allImages[currentIndex].url)
                    showDeleteDialog = false
                    if (allImages.size <= 1) navController.popBackStack()
                    else if (currentIndex >= allImages.size - 1) currentIndex = (currentIndex - 1).coerceAtLeast(0)
                }, colors = ButtonDefaults.buttonColors(containerColor = Colors.ErrorRed)) { Text("Delete") }
            },
            dismissButton = { OutlinedButton(onClick = { showDeleteDialog = false }) { Text("Cancel") } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(navController: NavHostController, feedbackViewModel: FeedbackViewModel = hiltViewModel()) {
    val uiState by feedbackViewModel.uiState.collectAsState()
    var message by remember { mutableStateOf("") }

    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            delay(1500)
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Send Feedback") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.White)
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = "feedback")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            if (uiState.success) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = Spacing.xl),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Spacing.md)
                ) {
                    Text("Thank you!", style = MaterialTheme.typography.headlineSmall, color = Colors.SuccessGreen)
                    Text("Your feedback was submitted anonymously.", style = MaterialTheme.typography.bodyMedium, color = Colors.MediumGray)
                }
            } else {
                Text("Share Your Feedback", style = MaterialTheme.typography.headlineSmall)
                Text("Your feedback is anonymous and will be analyzed automatically.", style = MaterialTheme.typography.bodyMedium, color = Colors.MediumGray)

                ShaleNammaTextField(
                    value = message,
                    onValueChange = { if (it.length <= 500) message = it },
                    label = "Feedback Message",
                    placeholder = "Type your feedback here..."
                )

                Text(
                    "${message.length}/500",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (message.length > 450) Colors.WarningOrange else Colors.MediumGray,
                    modifier = Modifier.align(Alignment.End)
                )

                if (uiState.errorMsg != null) {
                    Text("Error: ${uiState.errorMsg}", color = Colors.ErrorRed, style = MaterialTheme.typography.bodySmall)
                }

                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                Button(
                    onClick = {
                        if (message.isNotBlank()) {
                            feedbackViewModel.addFeedback(message, "", true, "")
                            message = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = message.isNotBlank() && !uiState.isLoading
                ) {
                    Text("Submit Feedback")
                }
            }
        }
    }
}

@Composable
fun NotificationsScreen(navController: NavHostController) { }

@Composable
fun AdminDashboardScreen(navController: NavHostController) { }
