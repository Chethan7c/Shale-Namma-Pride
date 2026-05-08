package com.shalenamma.pride.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.shalenamma.pride.data.models.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserSessionViewModel @Inject constructor(
    private val userRoleManager: UserRoleManager
) : ViewModel() {
    val userRole: StateFlow<UserRole> = userRoleManager.userRole
    val userId: StateFlow<String> = userRoleManager.userId
}
