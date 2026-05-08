package com.shalenamma.pride.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalenamma.pride.ai.GeminiHelper
import com.shalenamma.pride.data.models.Achievement
import com.shalenamma.pride.data.repository.AchievementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AchievementUiState(
    val achievements: List<Achievement> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AchievementViewModel @Inject constructor(
    private val achievementRepository: AchievementRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AchievementUiState())
    val uiState: StateFlow<AchievementUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            achievementRepository.getAllAchievements().collect { achievements ->
                Log.d("AchievementVM", "UI State updated with ${achievements.size} achievements")
                _uiState.value = _uiState.value.copy(achievements = achievements)
            }
        }
    }

    fun likeAchievement(achievementId: String, userId: String) {
        viewModelScope.launch {
            if (userId.isEmpty()) {
                Log.e("AchievementVM", "Cannot like: userId is empty")
                _uiState.value = _uiState.value.copy(error = "User not logged in properly")
                return@launch
            }
            Log.d("AchievementVM", "Liking achievement $achievementId with userId $userId")
            achievementRepository.likeAchievement(achievementId, userId)
                .onSuccess {
                    Log.d("AchievementVM", "Successfully liked achievement")
                }
                .onFailure { e ->
                    Log.e("AchievementVM", "Failed to like achievement: ${e.message}")
                    _uiState.value = _uiState.value.copy(error = e.message)
                }
        }
    }

    fun addAchievement(studentName: String, title: String, description: String = "", titleKn: String = "", descriptionKn: String = "", studentNameKn: String = "") {
        viewModelScope.launch {
            val finalTitleKn = if (titleKn.isEmpty()) GeminiHelper.translateToKannada(title) else titleKn
            val finalDescKn = if (descriptionKn.isEmpty()) GeminiHelper.translateToKannada(description) else descriptionKn
            val finalStudentNameKn = if (studentNameKn.isEmpty()) GeminiHelper.translateToKannada(studentName) else studentNameKn

            val achievement = Achievement(
                studentName = studentName,
                studentNameKn = finalStudentNameKn,
                title = title,
                description = description,
                titleKn = finalTitleKn,
                descriptionKn = finalDescKn,
                likes = 0,
                shares = 0,
                likedBy = emptyList()
            )
            achievementRepository.addAchievement(achievement)
                .onFailure { e -> _uiState.value = _uiState.value.copy(error = e.message) }
        }
    }

    fun updateAchievement(achievementId: String, studentName: String, title: String, titleKn: String = "", descriptionKn: String = "", studentNameKn: String = "") {
        viewModelScope.launch {
            achievementRepository.getAchievementById(achievementId).onSuccess { achievement ->
                val finalTitleKn = if (titleKn.isEmpty()) GeminiHelper.translateToKannada(achievement.title) else titleKn
                val finalDescKn = if (descriptionKn.isEmpty()) GeminiHelper.translateToKannada(achievement.description) else descriptionKn
                val finalStudentNameKn = if (studentNameKn.isEmpty()) GeminiHelper.translateToKannada(studentName) else studentNameKn

                val updated = achievement.copy(
                    studentName = studentName,
                    studentNameKn = finalStudentNameKn,
                    title = title,
                    titleKn = finalTitleKn,
                    descriptionKn = finalDescKn,
                    updatedAt = java.util.Date()
                )
                achievementRepository.updateAchievement(updated)
                    .onFailure { e -> _uiState.value = _uiState.value.copy(error = e.message) }
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun bulkTranslateAll() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val achievements = achievementRepository.getAllAchievements().first()
                Log.d("AchievementVM", "Bulk translate: found ${achievements.size} achievements")

                // Download ML Kit model once before starting
                try {
                    GeminiHelper.ensureTranslatorReady()
                } catch (e: Exception) {
                    Log.w("AchievementVM", "ML Kit model preload failed: ${e.message}")
                }

                achievements.forEachIndexed { index, achievement ->
                    try {
                        if (achievement.title.isBlank()) return@forEachIndexed
                        Log.d("AchievementVM", "Translating ${index + 1}/${achievements.size}: ${achievement.title}")

                        // FORCE translation - ignore existing titleKn
                        val titleKn = GeminiHelper.translateToKannada(achievement.title)

                        val descKn = if (achievement.description.isNotBlank()) {
                            GeminiHelper.translateToKannada(achievement.description)
                        } else achievement.descriptionKn

                        val nameKn = if (achievement.studentName.isNotBlank()) {
                            GeminiHelper.translateToKannada(achievement.studentName)
                        } else achievement.studentNameKn

                        val updated = achievement.copy(
                            titleKn = titleKn,
                            descriptionKn = descKn,
                            studentNameKn = nameKn,
                            updatedAt = java.util.Date()
                        )
                        achievementRepository.updateAchievement(updated)
                        Log.d("AchievementVM", "Translated: ${achievement.title} -> $titleKn")
                    } catch (e: Exception) {
                        Log.e("AchievementVM", "Failed to translate achievement: ${achievement.title}", e)
                    }
                }
                Log.d("AchievementVM", "Bulk translate completed")
            } catch (e: Exception) {
                Log.e("AchievementVM", "Bulk translate failed: ${e.message}", e)
                _uiState.value = _uiState.value.copy(error = "Bulk translate failed: ${e.message}")
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun deleteAchievement(achievementId: String) {
        viewModelScope.launch {
            achievementRepository.deleteAchievement(achievementId)
                .onSuccess {
                    Log.d("AchievementVM", "Successfully deleted achievement $achievementId")
                }
                .onFailure { e ->
                    Log.e("AchievementVM", "Failed to delete achievement: ${e.message}")
                    _uiState.value = _uiState.value.copy(error = e.message)
                }
        }
    }
}
