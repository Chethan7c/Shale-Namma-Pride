package com.shalenamma.pride.data.repository.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.shalenamma.pride.data.models.Announcement
import com.shalenamma.pride.data.repository.AnnouncementRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class AnnouncementRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AnnouncementRepository {

    override fun getAllAnnouncements(): Flow<List<Announcement>> = callbackFlow {
        val subscription = firestore.collection("announcements")
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val announcements = value?.toObjects(Announcement::class.java) ?: emptyList()
                trySend(announcements)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun addAnnouncement(announcement: Announcement): Result<Unit> {
        return try {
            val newAnnouncement = announcement.copy(
                id = firestore.collection("announcements").document().id,
                createdAt = Date(),
                updatedAt = Date()
            )
            firestore.collection("announcements").document(newAnnouncement.id).set(newAnnouncement).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAnnouncement(announcementId: String): Result<Unit> {
        return try {
            firestore.collection("announcements").document(announcementId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateAnnouncement(announcement: Announcement): Result<Unit> {
        return try {
            val updated = announcement.copy(updatedAt = Date())
            firestore.collection("announcements").document(announcement.id).set(updated).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
