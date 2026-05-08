package com.shalenamma.pride.ai

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private const val TAG = "GeminiHelper"

object GeminiHelper {

    // ML Kit Translator for English to Kannada (on-device, no API key needed)
    private var mlTranslator: Translator? = null

    private fun getTranslator(): Translator {
        if (mlTranslator == null) {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage("en")
                .setTargetLanguage("kn")
                .build()
            mlTranslator = Translation.getClient(options)
        }
        return mlTranslator!!
    }

    // Pre-download the translation model (call before bulk translation)
    suspend fun ensureTranslatorReady() {
        try {
            val translator = getTranslator()
            val conditions = DownloadConditions.Builder().build()
            translator.downloadModelIfNeeded(conditions).await()
            Log.d(TAG, "ML Kit model ready")
        } catch (e: Exception) {
            Log.w(TAG, "ML Kit model preload failed: ${e.message}")
        }
    }

    // Primary translation method - uses ML Kit (no API key needed)
    suspend fun translateToKannada(text: String): String {
        if (text.isBlank()) return text

        return withContext(Dispatchers.Default) {
            try {
                Log.d(TAG, "ML Kit translating: '$text'")
                val translator = getTranslator()

                // Download model if needed
                val conditions = DownloadConditions.Builder().build()
                try {
                    translator.downloadModelIfNeeded(conditions).await()
                } catch (e: Exception) {
                    Log.w(TAG, "Model download failed, trying translation anyway: ${e.message}")
                }

                val result = translator.translate(text).await()
                Log.d(TAG, "ML Kit RESULT: '$text' -> '$result'")
                result
            } catch (e: Exception) {
                Log.e(TAG, "ML Kit ERROR: '${e.message}' for '$text'", e)
                text
            }
        }
    }
}
