package com.shalenamma.pride.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shalenamma.pride.data.models.Feedback
import com.shalenamma.pride.data.models.UserRole
import com.shalenamma.pride.ui.components.ShaleNammaBadge
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.Spacing
import com.shalenamma.pride.ui.viewmodels.FeedbackViewModel
import com.shalenamma.pride.ui.viewmodels.UserSessionViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackListScreen(
    navController: NavHostController,
    feedbackViewModel: FeedbackViewModel = hiltViewModel(),
    sessionViewModel: UserSessionViewModel = hiltViewModel()
) {
    val allFeedback by feedbackViewModel.getAllFeedback().collectAsState(initial = emptyList())
    val userRole by sessionViewModel.userRole.collectAsState()
    val isAdminOrTeacher = userRole == UserRole.ADMIN || userRole == UserRole.TEACHER
    var showDeleteDialog by remember { mutableStateOf(false) }
    var feedbackToDelete by remember { mutableStateOf<Feedback?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Feedback List") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.White)
            )
        }
    ) { paddingValues ->
        if (allFeedback.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No feedback submitted yet", style = MaterialTheme.typography.bodyMedium, color = Colors.MediumGray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                items(allFeedback) { feedback ->
                    FeedbackItem(
                        feedback = feedback,
                        showDelete = isAdminOrTeacher,
                        onDeleteClick = {
                            feedbackToDelete = feedback
                            showDeleteDialog = true
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(Spacing.lg)) }
            }
        }
    }

    if (showDeleteDialog && feedbackToDelete != null && isAdminOrTeacher) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false; feedbackToDelete = null },
            title = { Text("Delete Feedback") },
            text = { Text("Are you sure you want to delete this feedback? This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        feedbackToDelete?.id?.let { feedbackId ->
                            feedbackViewModel.deleteFeedback(feedbackId) { success ->
                                if (success) { showDeleteDialog = false; feedbackToDelete = null }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Colors.ErrorRed)
                ) { Text("Delete") }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDeleteDialog = false; feedbackToDelete = null }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun FeedbackItem(feedback: Feedback, showDelete: Boolean = false, onDeleteClick: () -> Unit = {}) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()) }
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Colors.White)) {
        Column(modifier = Modifier.fillMaxWidth().padding(Spacing.md), verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ShaleNammaBadge(text = feedback.category.ifEmpty { "OTHER" }, backgroundColor = Colors.TrustBlue)
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                    if (feedback.urgency == "URGENT") {
                        ShaleNammaBadge(text = "URGENT", backgroundColor = Colors.ErrorRed)
                    }
                    val sentimentColor = when (feedback.sentiment) {
                        "POSITIVE" -> Colors.SuccessGreen
                        "NEGATIVE" -> Colors.ErrorRed
                        else -> Colors.MediumGray
                    }
                    if (feedback.sentiment.isNotEmpty()) {
                        ShaleNammaBadge(text = feedback.sentiment, backgroundColor = sentimentColor)
                    }
                    if (feedback.isAnonymous) {
                        ShaleNammaBadge(text = "Anonymous", backgroundColor = Colors.MediumGray)
                    }
                    if (showDelete) {
                        IconButton(onClick = onDeleteClick, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Colors.ErrorRed, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
            Text(text = feedback.message, style = MaterialTheme.typography.bodyMedium)
            if (feedback.summary.isNotEmpty()) {
                Text(text = "AI Summary: ${feedback.summary}", style = MaterialTheme.typography.bodySmall, color = Colors.MediumGray)
            }
            Text(text = dateFormat.format(feedback.createdAt), style = MaterialTheme.typography.bodySmall, color = Colors.MediumGray)
        }
    }
}
