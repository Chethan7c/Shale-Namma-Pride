package com.shalenamma.pride.data.repository.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.shalenamma.pride.data.models.Meal
import com.shalenamma.pride.data.repository.MealRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : MealRepository {

    override fun getAllMeals(): Flow<List<Meal>> = callbackFlow {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val subscription = firestore.collection("meals")
            .whereLessThan("date", today)
            .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val meals = value?.toObjects(Meal::class.java) ?: emptyList()
                trySend(meals)
            }
        awaitClose { subscription.remove() }
    }

    override fun getTodaysMeal(): Flow<Meal?> = callbackFlow {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val tomorrow = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val subscription = firestore.collection("meals")
            .whereGreaterThanOrEqualTo("date", today)
            .whereLessThan("date", tomorrow)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val meals = value?.toObjects(Meal::class.java) ?: emptyList()
                trySend(meals.firstOrNull())
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun getMealById(mealId: String): Result<Meal> {
        return try {
            val doc = firestore.collection("meals").document(mealId).get().await()
            val meal = doc.toObject(Meal::class.java)
            if (meal != null) Result.success(meal) else Result.failure(Exception("Meal not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun rateMeal(mealId: String, rating: Float): Result<Unit> {
        return try {
            val mealRef = firestore.collection("meals").document(mealId)
            val currentMeal = mealRef.get().await().toObject(Meal::class.java)
                ?: throw Exception("Meal not found")

            val newTotalRatings = currentMeal.totalRatings + 1
            val newRating = ((currentMeal.rating * currentMeal.totalRatings) + rating) / newTotalRatings

            mealRef.update(
                mapOf(
                    "rating" to newRating,
                    "totalRatings" to newTotalRatings,
                    "updatedAt" to Date()
                )
            ).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addMeal(meal: Meal): Result<String> {
        return try {
            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            val tomorrow = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            val existingDocs = firestore.collection("meals")
                .whereGreaterThanOrEqualTo("date", today)
                .whereLessThan("date", tomorrow)
                .get()
                .await()

            if (!existingDocs.isEmpty) {
                Result.failure(Exception("Meal already updated for today. Please try again tomorrow."))
            } else {
                val docRef = firestore.collection("meals").add(meal).await()
                Result.success(docRef.id)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateMeal(meal: Meal): Result<Unit> {
        return try {
            firestore.collection("meals").document(meal.id).set(meal).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteMeal(mealId: String): Result<Unit> {
        return try {
            firestore.collection("meals").document(mealId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
