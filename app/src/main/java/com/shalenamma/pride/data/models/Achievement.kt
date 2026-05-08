package com.shalenamma.pride.data.models

import com.google.firebase.firestore.DocumentId
import java.util.*

data class Achievement(
    @DocumentId
    val id: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val studentNameKn: String = "",
    val title: String = "",
    val description: String = "",
    val titleKn: String = "",
    val descriptionKn: String = "",
    val category: AchievementCategory = AchievementCategory.ACADEMIC,
    val imageUrl: String = "",
    val date: Date = Date(),
    val likes: Int = 0,
    val likedBy: List<String> = emptyList(),
    val shares: Int = 0,
    val createdBy: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class AchievementCategory {
    ACADEMIC,
    SPORTS,
    ARTS,
    BEHAVIORAL,
    TECHNICAL,
    OTHER
}
