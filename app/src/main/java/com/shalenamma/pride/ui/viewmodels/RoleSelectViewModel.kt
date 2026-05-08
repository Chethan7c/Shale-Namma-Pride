package com.shalenamma.pride.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.shalenamma.pride.data.models.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoleSelectViewModel @Inject constructor(
    private val userRoleManager: UserRoleManager
) : ViewModel() {
    fun selectTeacher() {
        userRoleManager.setRole(UserRole.TEACHER)
    }

    fun selectParent() {
        userRoleManager.setRole(UserRole.PARENT)
    }
}
