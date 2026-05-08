package com.shalenamma.pride.data.models

import com.google.firebase.firestore.DocumentId
import java.util.*

data class Notification(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val body: String = "",
    val type: NotificationType = NotificationType.GENERAL,
    val relatedId: String = "",
    val isRead: Boolean = false,
    val imageUrl: String = "",
    val createdAt: Date = Date(),
    val readAt: Date? = null
)

enum class NotificationType {
    MEAL_UPDATE,
    ACHIEVEMENT,
    ANNOUNCEMENT,
    FEEDBACK,
    GENERAL
}
