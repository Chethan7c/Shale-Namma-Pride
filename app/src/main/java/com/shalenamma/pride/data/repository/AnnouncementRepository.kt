package com.shalenamma.pride.data.repository

import com.shalenamma.pride.data.models.Announcement
import kotlinx.coroutines.flow.Flow

interface AnnouncementRepository {
    fun getAllAnnouncements(): Flow<List<Announcement>>
    suspend fun addAnnouncement(announcement: Announcement): Result<Unit>
    suspend fun deleteAnnouncement(announcementId: String): Result<Unit>
    suspend fun updateAnnouncement(announcement: Announcement): Result<Unit>
}
