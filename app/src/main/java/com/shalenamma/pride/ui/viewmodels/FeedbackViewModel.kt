package com.shalenamma.pride.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalenamma.pride.ai.SentimentAnalyzer
import com.shalenamma.pride.data.models.Feedback
import com.shalenamma.pride.data.models.Result
import com.shalenamma.pride.data.repository.FeedbackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FeedbackUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val success: Boolean = false
)

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedbackUiState())
    val uiState: StateFlow<FeedbackUiState> = _uiState.asStateFlow()

    fun addFeedback(message: String, category: String, isAnonymous: Boolean, userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMsg = null, success = false)

            val sentimentResult = SentimentAnalyzer.analyzeSentiment(message)

            val feedback = Feedback(
                message = message,
                category = if (category.isEmpty()) sentimentResult.category else category,
                isAnonymous = true,
                userId = "",
                sentiment = sentimentResult.sentiment,
                urgency = sentimentResult.urgency,
                summary = sentimentResult.summary,
                createdAt = java.util.Date()
            )
            feedbackRepository.addFeedback(feedback)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false, success = true)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMsg = e.message)
                }
        }
    }

    fun clearState() {
        _uiState.value = FeedbackUiState()
    }

    fun getAllFeedback(): Flow<List<Feedback>> {
        return feedbackRepository.getAllFeedback()
    }

    fun deleteFeedback(feedbackId: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = feedbackRepository.deleteFeedback(feedbackId)
            result.onSuccess {
                _uiState.value = _uiState.value.copy(success = true)
                onComplete(true)
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(errorMsg = e.message)
                onComplete(false)
            }
        }
    }
}
