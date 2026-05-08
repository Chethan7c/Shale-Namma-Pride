package com.shalenamma.pride.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalenamma.pride.data.models.Notification
import com.shalenamma.pride.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationUiState(
    val notifications: List<Notification> = emptyList(),
    val unreadCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState(isLoading = true))
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    fun loadNotifications(userId: String) {
        viewModelScope.launch {
            notificationRepository.getUserNotifications(userId).collect { notifications ->
                _uiState.update { state ->
                    state.copy(
                        notifications = notifications,
                        unreadCount = notifications.count { !it.isRead },
                        isLoading = false
                    )
                }
            }
        }
    }

    fun loadUnreadNotifications(userId: String) {
        viewModelScope.launch {
            notificationRepository.getUnreadNotifications(userId).collect { notifications ->
                _uiState.update { state ->
                    state.copy(
                        notifications = notifications,
                        unreadCount = notifications.size,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun markAsRead(userId: String, notificationId: String) {
        viewModelScope.launch {
            notificationRepository.markAsRead("$userId:$notificationId")
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message) }
                }
        }
    }

    fun deleteNotification(userId: String, notificationId: String) {
        viewModelScope.launch {
            notificationRepository.deleteNotification("$userId:$notificationId")
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message) }
                }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
