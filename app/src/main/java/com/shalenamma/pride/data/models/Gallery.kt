package com.shalenamma.pride.data.models

import com.google.firebase.firestore.DocumentId
import java.util.*

data class Gallery(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val thumbnailUrl: String = "",
    val images: List<GalleryImage> = emptyList(),
    val category: String = "",
    val date: Date = Date(),
    val views: Int = 0,
    val createdBy: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

data class GalleryImage(
    val id: String = "",
    val url: String = "",
    val caption: String = "",
    val uploadedAt: Date = Date()
)
