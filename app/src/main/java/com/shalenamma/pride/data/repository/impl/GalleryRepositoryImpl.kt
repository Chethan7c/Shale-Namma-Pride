package com.shalenamma.pride.data.repository.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.shalenamma.pride.data.models.Gallery
import com.shalenamma.pride.data.repository.GalleryRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : GalleryRepository {

    override fun getAllGalleries(): Flow<List<Gallery>> = callbackFlow {
        val subscription = firestore.collection("galleries")
            .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val galleries = value?.toObjects(Gallery::class.java) ?: emptyList()
                trySend(galleries)
            }
        awaitClose { subscription.remove() }
    }

    override fun getGalleriesByCategory(category: String): Flow<List<Gallery>> =
        callbackFlow {
            val subscription = firestore.collection("galleries")
                .whereEqualTo("category", category)
                .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val galleries = value?.toObjects(Gallery::class.java) ?: emptyList()
                    trySend(galleries)
                }
            awaitClose { subscription.remove() }
        }

    override suspend fun getGalleryById(galleryId: String): Result<Gallery> {
        return try {
            val doc = firestore.collection("galleries").document(galleryId).get().await()
            val gallery = doc.toObject(Gallery::class.java)
            if (gallery != null) Result.success(gallery) else Result.failure(Exception("Gallery not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addGallery(gallery: Gallery): Result<String> {
        return try {
            val docRef = firestore.collection("galleries").add(gallery).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateGallery(gallery: Gallery): Result<Unit> {
        return try {
            firestore.collection("galleries").document(gallery.id).set(gallery).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteGallery(galleryId: String): Result<Unit> {
        return try {
            firestore.collection("galleries").document(galleryId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteGalleryImage(imageUrl: String): Result<Unit> {
        return try {
            val galleries = firestore.collection("galleries").get().await()
            for (doc in galleries.documents) {
                val gallery = doc.toObject(Gallery::class.java)
                if (gallery != null) {
                    val updatedImages = gallery.images.filter { it.url != imageUrl }
                    if (updatedImages.size < gallery.images.size) {
                        firestore.collection("galleries").document(doc.id)
                            .update("images", updatedImages).await()
                    }
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
