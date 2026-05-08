package com.shalenamma.pride.ui.viewmodels

import android.util.Log
import com.shalenamma.pride.data.models.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRoleManager @Inject constructor(
    private val deviceIdManager: DeviceIdManager
) {
    private val _userRole = MutableStateFlow<UserRole>(UserRole.PARENT)
    val userRole: StateFlow<UserRole> = _userRole.asStateFlow()

    private val _userId = MutableStateFlow<String>("")
    val userId: StateFlow<String> = _userId.asStateFlow()

    fun setRole(role: UserRole) {
        Log.d("UserRoleManager", "Setting role: $role")
        _userRole.value = role

        // If parent, use device ID; otherwise use the user ID that will be set separately
        if (role == UserRole.PARENT) {
            val deviceId = deviceIdManager.getDeviceId()
            _userId.value = deviceId
            Log.d("UserRoleManager", "Parent role set, using device ID: $deviceId")
        }
    }

    fun setUserId(id: String) {
        Log.d("UserRoleManager", "Setting userId: $id")
        _userId.value = id
    }
}
