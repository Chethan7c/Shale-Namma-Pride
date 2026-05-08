package com.shalenamma.pride.data.repository.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.shalenamma.pride.data.models.Achievement
import com.shalenamma.pride.data.repository.AchievementRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class AchievementRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AchievementRepository {

    override fun getAllAchievements(): Flow<List<Achievement>> = callbackFlow {
        val subscription = firestore.collection("achievements")
            .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("AchievementRepo", "Error fetching achievements", error)
                    close(error)
                    return@addSnapshotListener
                }
                val achievements = value?.toObjects(Achievement::class.java) ?: emptyList()
                Log.d("AchievementRepo", "Achievements updated: ${achievements.size} items")
                trySend(achievements)
            }
        awaitClose { subscription.remove() }
    }

    override fun getAchievementsByStudent(studentId: String): Flow<List<Achievement>> =
        callbackFlow {
            val subscription = firestore.collection("achievements")
                .whereEqualTo("studentId", studentId)
                .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val achievements = value?.toObjects(Achievement::class.java) ?: emptyList()
                    trySend(achievements)
                }
            awaitClose { subscription.remove() }
        }

    override suspend fun getAchievementById(achievementId: String): Result<Achievement> {
        return try {
            val doc = firestore.collection("achievements").document(achievementId).get().await()
            val achievement = doc.toObject(Achievement::class.java)
            if (achievement != null) Result.success(achievement)
            else Result.failure(Exception("Achievement not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addAchievement(achievement: Achievement): Result<String> {
        return try {
            val docRef = firestore.collection("achievements").add(achievement).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateAchievement(achievement: Achievement): Result<Unit> {
        return try {
            firestore.collection("achievements").document(achievement.id).set(
                achievement,
                com.google.firebase.firestore.SetOptions.merge()
            ).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAchievement(achievementId: String): Result<Unit> {
        return try {
            firestore.collection("achievements").document(achievementId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun likeAchievement(achievementId: String, userId: String): Result<Unit> {
        return try {
            if (userId.isEmpty()) {
                return Result.failure(Exception("User ID is empty"))
            }

            val doc = firestore.collection("achievements").document(achievementId).get().await()
            val achievement = doc.toObject(Achievement::class.java)
                ?: return Result.failure(Exception("Achievement not found"))

            val likedBy = achievement.likedBy.toMutableList()

            if (likedBy.contains(userId)) {
                return Result.failure(Exception("You have already liked this achievement"))
            }

            likedBy.add(userId)
            Log.d("AchievementRepo", "User $userId liked achievement. Total likes: ${likedBy.size}")

            firestore.collection("achievements").document(achievementId).update(
                mapOf(
                    "likes" to likedBy.size,
                    "likedBy" to likedBy,
                    "updatedAt" to Date()
                )
            ).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AchievementRepo", "Error liking achievement", e)
            Result.failure(e)
        }
    }

    override suspend fun shareAchievement(achievementId: String): Result<Unit> {
        return try {
            firestore.collection("achievements").document(achievementId).update(
                mapOf(
                    "shares" to com.google.firebase.firestore.FieldValue.increment(1),
                    "updatedAt" to Date()
                )
            ).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
