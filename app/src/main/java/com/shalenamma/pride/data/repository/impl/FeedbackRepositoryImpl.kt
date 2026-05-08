package com.shalenamma.pride.data.repository.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.shalenamma.pride.data.models.Feedback
import com.shalenamma.pride.data.models.Result
import com.shalenamma.pride.data.models.failure
import com.shalenamma.pride.data.models.success
import com.shalenamma.pride.data.repository.FeedbackRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FeedbackRepository {

    override suspend fun addFeedback(feedback: Feedback): Result<String> {
        return try {
            val docRef = firestore.collection("feedbacks").add(feedback).await()
            success(docRef.id)
        } catch (e: Exception) {
            failure(e)
        }
    }

    override fun getAllFeedback(): Flow<List<Feedback>> = callbackFlow {
        val subscription = firestore.collection("feedbacks")
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val feedbacks = value?.toObjects(Feedback::class.java) ?: emptyList()
                trySend(feedbacks)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun deleteFeedback(feedbackId: String): Result<Unit> {
        return try {
            firestore.collection("feedbacks").document(feedbackId).delete().await()
            success(Unit)
        } catch (e: Exception) {
            failure(e)
        }
    }
}
