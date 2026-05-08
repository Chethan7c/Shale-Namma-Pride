package com.shalenamma.pride.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalenamma.pride.data.models.User
import com.shalenamma.pride.data.models.UserRole
import com.shalenamma.pride.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRoleManager: UserRoleManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val isLoggedIn get() = authRepository.isUserLoggedIn()

    init {
        viewModelScope.launch {
            authRepository.getCurrentUser().collect { user ->
                if (user != null) {
                    Log.d("AuthViewModel", "Current user found: ${user.email}, userId: ${user.id}")
                    userRoleManager.setRole(user.role)
                    userRoleManager.setUserId(user.id)
                } else {
                    Log.d("AuthViewModel", "No current user")
                }
                _uiState.value = _uiState.value.copy(user = user)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            authRepository.loginWithEmail(email, password)
                .onSuccess { user ->
                    userRoleManager.setRole(user.role)
                    userRoleManager.setUserId(user.id)
                    _uiState.value = AuthUiState(user = user, isSuccess = true)
                }
                .onFailure { e ->
                    _uiState.value = AuthUiState(error = e.message)
                }
        }
    }

    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            authRepository.signUpWithEmail(email, password, name)
                .onSuccess { user ->
                    userRoleManager.setRole(user.role)
                    userRoleManager.setUserId(user.id)
                    _uiState.value = AuthUiState(user = user, isSuccess = true)
                }
                .onFailure { e ->
                    _uiState.value = AuthUiState(error = e.message)
                }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            userRoleManager.setRole(UserRole.PARENT)
            userRoleManager.setUserId("")
            _uiState.value = AuthUiState()
        }
    }

    fun updateUserProfile(name: String, profileImageUrl: String) {
        viewModelScope.launch {
            authRepository.updateUserProfile(name, profileImageUrl)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
