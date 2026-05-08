package com.shalenamma.pride.data.repository

import com.shalenamma.pride.data.models.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getUserNotifications(userId: String): Flow<List<Notification>>
    fun getUnreadNotifications(userId: String): Flow<List<Notification>>
    suspend fun markAsRead(notificationId: String): Result<Unit>
    suspend fun deleteNotification(notificationId: String): Result<Unit>
    suspend fun sendNotification(notification: Notification): Result<Unit>
}
