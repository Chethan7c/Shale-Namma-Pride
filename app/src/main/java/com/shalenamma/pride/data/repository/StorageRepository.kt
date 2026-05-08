package com.shalenamma.pride.data.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun uploadImage(path: String, uri: Uri): Result<String>
    suspend fun downloadImage(path: String): Result<Uri>
    suspend fun deleteImage(path: String): Result<Unit>
    suspend fun getDownloadUrl(path: String): Result<String>
}
