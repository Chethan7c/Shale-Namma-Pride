package com.shalenamma.pride.data.models

import com.google.firebase.firestore.DocumentId
import java.util.*

data class Meal(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val titleKn: String = "",
    val descriptionKn: String = "",
    val imageUrl: String = "",
    val items: List<String> = emptyList(),
    val nutritionInfo: NutritionInfo? = null,
    val date: Date = Date(),
    val rating: Float = 0f,
    val totalRatings: Int = 0,
    val createdBy: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

data class NutritionInfo(
    val calories: Int = 0,
    val protein: String = "",
    val carbs: String = "",
    val fat: String = "",
    val fiber: String = ""
)
