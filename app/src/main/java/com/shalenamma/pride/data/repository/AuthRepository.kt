package com.shalenamma.pride.data.repository

import com.shalenamma.pride.data.models.User
import com.shalenamma.pride.data.models.UserRole
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun loginWithEmail(email: String, password: String): Result<User>
    suspend fun signUpWithEmail(email: String, password: String, name: String): Result<User>
    suspend fun signOut()
    suspend fun updateUserProfile(name: String, profileImageUrl: String): Result<Unit>
    suspend fun resetPassword(email: String): Result<Unit>
    fun isUserLoggedIn(): Boolean
    suspend fun isEmailAllowed(email: String): Result<UserRole>
}
