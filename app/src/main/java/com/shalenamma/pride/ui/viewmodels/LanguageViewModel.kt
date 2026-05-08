package com.shalenamma.pride.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    val languagePreferenceManager: LanguagePreferenceManager
) : ViewModel()
