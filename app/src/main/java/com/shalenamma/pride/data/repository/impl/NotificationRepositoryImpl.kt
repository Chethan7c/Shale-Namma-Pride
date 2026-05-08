package com.shalenamma.pride.data.repository.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.shalenamma.pride.data.models.Notification
import com.shalenamma.pride.data.repository.NotificationRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : NotificationRepository {

    override fun getUserNotifications(userId: String): Flow<List<Notification>> =
        callbackFlow {
            val subscription = firestore.collection("notifications")
                .document(userId)
                .collection("messages")
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val notifications = value?.toObjects(Notification::class.java) ?: emptyList()
                    trySend(notifications)
                }
            awaitClose { subscription.remove() }
        }

    override fun getUnreadNotifications(userId: String): Flow<List<Notification>> =
        callbackFlow {
            val subscription = firestore.collection("notifications")
                .document(userId)
                .collection("messages")
                .whereEqualTo("isRead", false)
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val notifications = value?.toObjects(Notification::class.java) ?: emptyList()
                    trySend(notifications)
                }
            awaitClose { subscription.remove() }
        }

    override suspend fun markAsRead(notificationId: String): Result<Unit> {
        return try {
            val parts = notificationId.split(":")
            if (parts.size != 2) return Result.failure(Exception("Invalid notification ID format"))
            val userId = parts[0]
            val docId = parts[1]
            firestore.collection("notifications")
                .document(userId)
                .collection("messages")
                .document(docId)
                .update("isRead", true, "readAt", Date())
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteNotification(notificationId: String): Result<Unit> {
        return try {
            val parts = notificationId.split(":")
            if (parts.size != 2) return Result.failure(Exception("Invalid notification ID format"))
            val userId = parts[0]
            val docId = parts[1]
            firestore.collection("notifications")
                .document(userId)
                .collection("messages")
                .document(docId)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendNotification(notification: Notification): Result<Unit> {
        return try {
            firestore.collection("notifications")
                .document(notification.userId)
                .collection("messages")
                .add(notification)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
