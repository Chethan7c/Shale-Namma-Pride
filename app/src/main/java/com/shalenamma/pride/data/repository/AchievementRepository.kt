package com.shalenamma.pride.data.repository

import com.shalenamma.pride.data.models.Achievement
import kotlinx.coroutines.flow.Flow

interface AchievementRepository {
    fun getAllAchievements(): Flow<List<Achievement>>
    fun getAchievementsByStudent(studentId: String): Flow<List<Achievement>>
    suspend fun getAchievementById(achievementId: String): Result<Achievement>
    suspend fun addAchievement(achievement: Achievement): Result<String>
    suspend fun updateAchievement(achievement: Achievement): Result<Unit>
    suspend fun deleteAchievement(achievementId: String): Result<Unit>
    suspend fun likeAchievement(achievementId: String, userId: String): Result<Unit>
    suspend fun shareAchievement(achievementId: String): Result<Unit>
}
