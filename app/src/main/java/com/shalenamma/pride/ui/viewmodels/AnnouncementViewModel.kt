package com.shalenamma.pride.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalenamma.pride.ai.GeminiHelper
import com.shalenamma.pride.data.models.Announcement
import com.shalenamma.pride.data.repository.AnnouncementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

data class AnnouncementUiState(
    val announcements: List<Announcement> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val announcementRepository: AnnouncementRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnnouncementUiState())
    val uiState: StateFlow<AnnouncementUiState> = _uiState.asStateFlow()

    init {
        loadAnnouncements()
    }

    private fun loadAnnouncements() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            announcementRepository.getAllAnnouncements().collect { announcements ->
                _uiState.value = _uiState.value.copy(
                    announcements = announcements.sortedByDescending { it.createdAt },
                    isLoading = false
                )
            }
        }
    }

    fun addAnnouncement(title: String, content: String, createdBy: String, titleKn: String = "", contentKn: String = "") {
        viewModelScope.launch {
            val finalTitleKn = if (titleKn.isEmpty()) GeminiHelper.translateToKannada(title) else titleKn
            val finalContentKn = if (contentKn.isEmpty()) GeminiHelper.translateToKannada(content) else contentKn

            val announcement = Announcement(
                title = title,
                content = content,
                titleKn = finalTitleKn,
                contentKn = finalContentKn,
                createdBy = createdBy
            )
            announcementRepository.addAnnouncement(announcement)
                .onSuccess { loadAnnouncements() }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(error = e.message)
                }
        }
    }

    fun deleteAnnouncement(announcementId: String) {
        viewModelScope.launch {
            announcementRepository.deleteAnnouncement(announcementId)
                .onSuccess { loadAnnouncements() }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(error = e.message)
                }
        }
    }

    fun bulkTranslateAll() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val announcements = announcementRepository.getAllAnnouncements().first()
                Log.d("AnnouncementVM", "Bulk translate: found ${announcements.size} announcements")
                announcements.forEachIndexed { index, announcement ->
                    try {
                        Log.d("AnnouncementVM", "Translating ${index + 1}/${announcements.size}: ${announcement.title}")
                        val titleKn = if (announcement.titleKn.isEmpty()) {
                            delay(1500)
                            GeminiHelper.translateToKannada(announcement.title)
                        } else announcement.titleKn
                        val contentKn = if (announcement.contentKn.isEmpty()) {
                            delay(1500)
                            GeminiHelper.translateToKannada(announcement.content)
                        } else announcement.contentKn
                        val updated = announcement.copy(
                            titleKn = titleKn,
                            contentKn = contentKn,
                            updatedAt = java.util.Date()
                        )
                        announcementRepository.updateAnnouncement(updated)
                        Log.d("AnnouncementVM", "Translated: ${announcement.title} -> $titleKn")
                    } catch (e: Exception) {
                        Log.e("AnnouncementVM", "Failed to translate: ${announcement.title}", e)
                    }
                }
                Log.d("AnnouncementVM", "Bulk translate completed")
            } catch (e: Exception) {
                Log.e("AnnouncementVM", "Bulk translate failed: ${e.message}", e)
                _uiState.value = _uiState.value.copy(error = "Bulk translate failed: ${e.message}")
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
