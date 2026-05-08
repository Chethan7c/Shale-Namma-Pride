package com.shalenamma.pride.ui.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalenamma.pride.ai.GeminiHelper
import com.shalenamma.pride.data.models.Meal
import com.shalenamma.pride.data.remote.CloudinaryService
import com.shalenamma.pride.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MealViewModel"

data class MealUiState(
    val meals: List<Meal> = emptyList(),
    val todaysMeal: Meal? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val cloudinaryService: CloudinaryService,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealUiState())
    val uiState: StateFlow<MealUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            mealRepository.getAllMeals().collect { meals ->
                _uiState.value = _uiState.value.copy(meals = meals)
            }
        }
        viewModelScope.launch {
            mealRepository.getTodaysMeal().collect { meal ->
                _uiState.value = _uiState.value.copy(todaysMeal = meal)
            }
        }
    }

    fun rateMeal(mealId: String, rating: Float) {
        viewModelScope.launch {
            mealRepository.rateMeal(mealId, rating)
                .onFailure { e -> _uiState.value = _uiState.value.copy(error = e.message) }
        }
    }

    fun addTodaysMeal(name: String, description: String, imageUri: Uri? = null) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "addTodaysMeal called: name=$name, hasImage=${imageUri != null}")
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                var imageUrl = ""
                var aiDescription = ""

                if (imageUri != null) {
                    Log.d(TAG, "Processing image: $imageUri")
                    val inputStream = context.contentResolver.openInputStream(imageUri)
                        ?: throw Exception("Cannot open image file")

                    val tempFile = java.io.File(context.cacheDir, "temp_meal_${System.currentTimeMillis()}.jpg")
                    Log.d(TAG, "Created temp file: ${tempFile.absolutePath}")

                    tempFile.outputStream().use { output ->
                        inputStream.copyTo(output)
                    }
                    inputStream.close()
                    Log.d(TAG, "Temp file size: ${tempFile.length()} bytes")

                    Log.d(TAG, "Starting Cloudinary upload...")
                    val uploadResult = cloudinaryService.uploadImage(tempFile, "meals")
                    Log.d(TAG, "Upload result: $uploadResult")

                    tempFile.delete()
                    Log.d(TAG, "Temp file deleted")

                    if (uploadResult.isSuccess) {
                        imageUrl = uploadResult.getOrNull() ?: ""
                        Log.d(TAG, "Upload successful, URL: $imageUrl")
                        if (imageUrl.isEmpty()) {
                            throw Exception("Upload returned empty URL")
                        }
                    } else {
                        val error = uploadResult.exceptionOrNull()
                        Log.e(TAG, "Upload failed", error)
                        throw error ?: Exception("Upload failed")
                    }
                }

                val finalDescription = if (aiDescription.isNotEmpty()) aiDescription else description

                // Use ML Kit for translations (no local map)
                val finalTitleKn = GeminiHelper.translateToKannada(name)
                val finalDescKn = if (finalDescription.isNotEmpty()) {
                    GeminiHelper.translateToKannada(finalDescription)
                } else ""

                val meal = Meal(
                    title = name,
                    description = finalDescription,
                    titleKn = finalTitleKn,
                    descriptionKn = finalDescKn,
                    imageUrl = imageUrl,
                    date = java.util.Date()
                )

                Log.d(TAG, "Saving meal to repository: $meal")
                mealRepository.addMeal(meal)
                    .onSuccess {
                        Log.d(TAG, "Meal saved successfully")
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                    .onFailure { e ->
                        Log.e(TAG, "Failed to save meal", e)
                        _uiState.value = _uiState.value.copy(
                            error = "Failed to save meal: ${e.message}",
                            isLoading = false
                        )
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in addTodaysMeal", e)
                _uiState.value = _uiState.value.copy(
                    error = "Error: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun deleteMeal(mealId: String) {
        viewModelScope.launch {
            mealRepository.deleteMeal(mealId)
                .onFailure { e -> _uiState.value = _uiState.value.copy(error = e.message) }
        }
    }

    fun bulkTranslateAll() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Pre-download ML Kit model
                try {
                    GeminiHelper.ensureTranslatorReady()
                } catch (e: Exception) {
                    Log.w("MealVM", "ML Kit model preload failed: ${e.message}")
                }

                val meals = mealRepository.getAllMeals().first()
                val todaysMeal = mealRepository.getTodaysMeal().first()
                val allMeals = if (todaysMeal != null && !meals.any { it.id == todaysMeal.id }) {
                    listOf(todaysMeal) + meals
                } else {
                    meals
                }
                Log.d("MealVM", "Bulk translate: found ${allMeals.size} meals (including today's meal)")
                allMeals.forEachIndexed { index, meal ->
                    try {
                        Log.d("MealVM", "Translating ${index + 1}/${allMeals.size}: ${meal.title}")
                        // Use ML Kit for all translations (no local map)
                        val titleKn = GeminiHelper.translateToKannada(meal.title)

                        val descKn = if (meal.description.isNotEmpty()) {
                            Log.d("MealVM", "Translating description: '${meal.description}'")
                            GeminiHelper.translateToKannada(meal.description)
                        } else ""

                        val updated = meal.copy(
                            titleKn = titleKn,
                            descriptionKn = descKn,
                            updatedAt = java.util.Date()
                        )
                        mealRepository.updateMeal(updated)
                        Log.d("MealVM", "Translated: ${meal.title} -> $titleKn")
                    } catch (e: Exception) {
                        Log.e("MealVM", "Failed to translate: ${meal.title}", e)
                    }
                }
                Log.d("MealVM", "Bulk translate completed")
            } catch (e: Exception) {
                Log.e("MealVM", "Bulk translate failed: ${e.message}", e)
                _uiState.value = _uiState.value.copy(error = "Bulk translate failed: ${e.message}")
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
