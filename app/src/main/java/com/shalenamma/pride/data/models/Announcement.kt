package com.shalenamma.pride.data.models

import com.google.firebase.firestore.DocumentId
import java.util.*

data class Announcement(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val titleKn: String = "",
    val contentKn: String = "",
    val createdBy: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
