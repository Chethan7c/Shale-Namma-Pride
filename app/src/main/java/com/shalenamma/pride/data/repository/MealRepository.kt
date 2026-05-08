package com.shalenamma.pride.data.repository

import com.shalenamma.pride.data.models.Meal
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    fun getAllMeals(): Flow<List<Meal>>
    fun getTodaysMeal(): Flow<Meal?>
    suspend fun getMealById(mealId: String): Result<Meal>
    suspend fun rateMeal(mealId: String, rating: Float): Result<Unit>
    suspend fun addMeal(meal: Meal): Result<String>
    suspend fun updateMeal(meal: Meal): Result<Unit>
    suspend fun deleteMeal(mealId: String): Result<Unit>
}
