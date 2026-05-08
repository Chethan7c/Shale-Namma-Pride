package com.shalenamma.pride.data.models

import com.google.firebase.firestore.DocumentId
import java.util.*

data class Feedback(
    @DocumentId
    val id: String = "",
    val message: String = "",
    val category: String = "",
    val isAnonymous: Boolean = false,
    val userId: String = "",
    val sentiment: String = "", // POSITIVE, NEGATIVE, NEUTRAL
    val urgency: String = "", // URGENT, NORMAL
    val summary: String = "", // Brief summary from AI
    val createdAt: Date = Date()
)
