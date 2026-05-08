package com.shalenamma.pride.ai

import android.util.Log

private const val TAG = "SentimentAnalyzer"

data class SentimentResult(
    val sentiment: String,
    val category: String,
    val urgency: String,
    val summary: String
)

object SentimentAnalyzer {

    fun analyzeSentiment(text: String): SentimentResult {
        Log.d(TAG, "Analyzing sentiment for: '$text'")
        return keywordAnalysis(text)
    }

    private fun keywordAnalysis(text: String): SentimentResult {
        val lowerText = text.lowercase()

        // Positive words with weights
        val positiveWords = mapOf(
            "excellent" to 3, "amazing" to 3, "outstanding" to 3, "wonderful" to 3,
            "great" to 2, "good" to 2, "happy" to 2, "love" to 2, "best" to 2,
            "nice" to 1, "ok" to 1, "fine" to 1, "better" to 1, "improved" to 1,
            "helpful" to 1, "satisfied" to 1, "appreciate" to 2, "thank" to 2,
            "delicious" to 2, "tasty" to 2, "clean" to 1, "working" to 1
        )

        // Negative words with weights
        val negativeWords = mapOf(
            "terrible" to 3, "horrible" to 3, "awful" to 3, "worst" to 3, "hate" to 3,
            "bad" to 2, "poor" to 2, "worse" to 2, "disgusting" to 3, "dirty" to 2,
            "broken" to 2, "not working" to 2, "issue" to 1, "problem" to 1, "sad" to 1,
            "angry" to 2, "disappointed" to 2, "waste" to 2, "slow" to 1, "late" to 1,
            "cold" to 1, "spoiled" to 2, "stale" to 2
        )

        // Negation words that flip sentiment
        val negationWords = listOf("not", "no", "never", "don't", "doesn't", "didn't", "won't", "can't", "isn't", "aren't")

        // Calculate scores
        var posScore = 0
        var negScore = 0

        // Check for positive words
        for ((word, weight) in positiveWords) {
            if (lowerText.contains(word)) {
                // Check if there's a negation near this word (within 3 words)
                val isNegated = isWordNegated(lowerText, word, negationWords)
                if (!isNegated) {
                    posScore += weight
                } else {
                    negScore += weight // Negated positive = negative
                }
            }
        }

        // Check for negative words
        for ((word, weight) in negativeWords) {
            if (lowerText.contains(word)) {
                val isNegated = isWordNegated(lowerText, word, negationWords)
                if (!isNegated) {
                    negScore += weight
                } else {
                    posScore += weight // Negated negative = positive
                }
            }
        }

        // Determine sentiment based on weighted scores
        val sentiment = when {
            posScore > negScore + 1 -> "POSITIVE"  // Need clear positive signal
            negScore > posScore + 1 -> "NEGATIVE"  // Need clear negative signal
            else -> "NEUTRAL"
        }

        val (category, urgency) = extractExtras(lowerText)
        val summary = generateSummary(lowerText, sentiment)

        Log.d(TAG, "Result: sentiment=$sentiment, posScore=$posScore, negScore=$negScore")
        return SentimentResult(sentiment, category, urgency, summary)
    }

    private fun isWordNegated(text: String, targetWord: String, negations: List<String>): Boolean {
        val words = text.split(Regex("\\s+"))
        val targetIndex = words.indexOfFirst { it.contains(targetWord) }
        if (targetIndex < 0) return false

        // Check for negation within 3 words before the target
        val startIndex = maxOf(0, targetIndex - 3)
        for (i in startIndex until targetIndex) {
            if (negations.any { words[i].contains(it) }) {
                return true
            }
        }
        return false
    }

    private fun extractExtras(text: String): Pair<String, String> {
        val category = when {
            text.contains(Regex("food|meal|lunch|dinner|rice|sambar|curry|tasty|spoiled|hungry|breakfast")) -> "MEAL_ISSUE"
            text.contains(Regex("classroom|toilet|bathroom|water|fan|light|building|infrastructure|repair")) -> "FACILITY"
            text.contains(Regex("teacher|teaching|class|learn|study|homework|exam|education|lesson")) -> "TEACHING"
            text.contains(Regex("safe|unsafe|bully|fight|injured|hurt|security|harassment|violence")) -> "SAFETY"
            else -> "OTHER"
        }
        val urgency = if (text.contains(Regex("urgent|immediately|emergency|danger|unsafe|asap|quickly"))) "URGENT" else "NORMAL"
        return Pair(category, urgency)
    }

    private fun generateSummary(text: String, sentiment: String): String {
        val words = text.split(Regex("\\s+")).take(8)
        val capitalizedSentiment = sentiment.lowercase().replaceFirstChar { it.uppercase() }
        return "$capitalizedSentiment feedback: ${words.joinToString(" ")}..."
    }
}
