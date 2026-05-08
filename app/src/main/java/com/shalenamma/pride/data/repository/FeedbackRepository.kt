package com.shalenamma.pride.data.repository

import com.shalenamma.pride.data.models.Feedback
import kotlinx.coroutines.flow.Flow
import com.shalenamma.pride.data.models.Result

interface FeedbackRepository {
    suspend fun addFeedback(feedback: Feedback): Result<String>
    fun getAllFeedback(): Flow<List<Feedback>>
    suspend fun deleteFeedback(feedbackId: String): Result<Unit>
}
