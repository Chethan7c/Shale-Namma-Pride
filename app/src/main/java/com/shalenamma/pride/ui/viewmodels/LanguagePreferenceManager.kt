package com.shalenamma.pride.ui.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguagePreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val LANGUAGE_KEY = "language"

    private val _languageFlow = MutableStateFlow(getLanguage())
    val languageFlow: StateFlow<String> = _languageFlow.asStateFlow()

    init {
        Log.d("LanguagePref", "Initial language from prefs: ${getLanguage()}")
    }

    fun getLanguage(): String {
        return prefs.getString(LANGUAGE_KEY, "en") ?: "en"
    }

    fun setLanguage(lang: String) {
        Log.d("LanguagePref", "setLanguage called with: $lang")
        prefs.edit().putString(LANGUAGE_KEY, lang).apply()
        _languageFlow.value = lang
        Log.d("LanguagePref", "Flow updated, new value: ${_languageFlow.value}")
    }

    fun isKannada(): Boolean {
        return getLanguage() == "kn"
    }
}
