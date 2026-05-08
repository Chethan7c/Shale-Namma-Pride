package com.shalenamma.pride.ui.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalenamma.pride.data.models.Gallery
import com.shalenamma.pride.data.remote.CloudinaryService
import com.shalenamma.pride.data.repository.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "GalleryViewModel"

data class GalleryUiState(
    val galleries: List<Gallery> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val cloudinaryService: CloudinaryService,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(GalleryUiState())
    val uiState: StateFlow<GalleryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            galleryRepository.getAllGalleries().collect { galleries ->
                _uiState.value = _uiState.value.copy(galleries = galleries)
            }
        }
    }

    fun filterByCategory(category: String) {
        viewModelScope.launch {
            galleryRepository.getGalleriesByCategory(category).collect { galleries ->
                _uiState.value = _uiState.value.copy(galleries = galleries)
            }
        }
    }

    // Wrapper for backward compatibility (called by dead dialog code)
    fun addGalleryImage(title: String, imageUri: Uri) {
        addGalleryImages(title, listOf(imageUri))
    }

    fun addGalleryImages(title: String, imageUris: List<Uri>) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "addGalleryImages called: title=$title, count=${imageUris.size}")
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                val galleryImages = mutableListOf<com.shalenamma.pride.data.models.GalleryImage>()

                imageUris.forEachIndexed { index, uri ->
                    Log.d(TAG, "Processing image ${index + 1}/${imageUris.size}: $uri")
                    val inputStream = context.contentResolver.openInputStream(uri)
                        ?: throw Exception("Cannot open image file $uri")

                    val tempFile = java.io.File(context.cacheDir, "temp_gallery_${System.currentTimeMillis()}_$index.jpg")
                    Log.d(TAG, "Created temp file: ${tempFile.absolutePath}")

                    tempFile.outputStream().use { output ->
                        inputStream.copyTo(output)
                    }
                    inputStream.close()
                    Log.d(TAG, "Temp file size: ${tempFile.length()} bytes")

                    Log.d(TAG, "Starting Cloudinary upload for image ${index + 1}...")
                    val uploadResult = cloudinaryService.uploadImage(tempFile, "gallery")
                    Log.d(TAG, "Upload result: $uploadResult")

                    tempFile.delete()
                    Log.d(TAG, "Temp file deleted")

                    if (!uploadResult.isSuccess) {
                        val error = uploadResult.exceptionOrNull()
                        Log.e(TAG, "Upload failed for image ${index + 1}", error)
                        throw error ?: Exception("Upload failed for image ${index + 1}")
                    }

                    val imageUrl = uploadResult.getOrNull() ?: ""
                    Log.d(TAG, "Upload successful, URL: $imageUrl")

                    if (imageUrl.isEmpty()) {
                        throw Exception("Upload returned empty URL for image ${index + 1}")
                    }

                    galleryImages.add(
                        com.shalenamma.pride.data.models.GalleryImage(url = imageUrl)
                    )
                }

                val gallery = com.shalenamma.pride.data.models.Gallery(
                    title = title,
                    images = galleryImages.toList()
                )

                Log.d(TAG, "Saving gallery to repository: $gallery")
                galleryRepository.addGallery(gallery)
                    .onSuccess {
                        Log.d(TAG, "Gallery saved successfully with ${galleryImages.size} images")
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                    .onFailure { e ->
                        Log.e(TAG, "Failed to save gallery", e)
                        _uiState.value = _uiState.value.copy(
                            error = "Failed to save gallery: ${e.message}",
                            isLoading = false
                        )
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in addGalleryImages", e)
                _uiState.value = _uiState.value.copy(
                    error = "Image upload error: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun addImagesDirectly(imageUris: List<Uri>) {
        addGalleryImages("", imageUris)
    }

    fun deleteGalleryImage(imageUrl: String) {
        viewModelScope.launch {
            galleryRepository.deleteGalleryImage(imageUrl)
                .onFailure { e -> _uiState.value = _uiState.value.copy(error = e.message) }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
