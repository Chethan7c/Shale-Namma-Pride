package com.shalenamma.pride.data.repository

import com.shalenamma.pride.data.models.Gallery
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {
    fun getAllGalleries(): Flow<List<Gallery>>
    fun getGalleriesByCategory(category: String): Flow<List<Gallery>>
    suspend fun getGalleryById(galleryId: String): Result<Gallery>
    suspend fun addGallery(gallery: Gallery): Result<String>
    suspend fun updateGallery(gallery: Gallery): Result<Unit>
    suspend fun deleteGallery(galleryId: String): Result<Unit>
    suspend fun deleteGalleryImage(imageUrl: String): Result<Unit>
}
