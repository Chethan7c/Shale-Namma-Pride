package com.shalenamma.pride.data.models

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class User(
    @DocumentId
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val role: UserRole = UserRole.STUDENT,
    val schoolId: String = "",
    val profileImageUrl: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val isActive: Boolean = true
) {
    // This secondary constructor ensures Firestore can deserialize the role safely
    // Note: Firestore uses the primary constructor but we can provide a property setter workaround

    companion object {
        // Helper for manual deserialization
        fun fromMap(map: Map<String, Any?>): User {
            val roleStr = (map["role"] as? String) ?: "STUDENT"
            val safeRole = UserRole.fromSafeString(roleStr)

            return User(
                id = map["id"] as? String ?: "",
                email = map["email"] as? String ?: "",
                name = map["name"] as? String ?: "",
                phoneNumber = map["phoneNumber"] as? String ?: "",
                role = safeRole,
                schoolId = map["schoolId"] as? String ?: "",
                profileImageUrl = map["profileImageUrl"] as? String ?: "",
                createdAt = (map["createdAt"] as? com.google.firebase.Timestamp)?.toDate() ?: Date(),
                updatedAt = (map["updatedAt"] as? com.google.firebase.Timestamp)?.toDate() ?: Date(),
                isActive = map["isActive"] as? Boolean ?: true
            )
        }
    }
}

enum class UserRole {
    ADMIN,
    TEACHER,
    STUDENT,
    PARENT;

    companion object {
        fun fromSafeString(value: String?): UserRole {
            if (value == null) return STUDENT
            return try {
                // Clean up: remove quotes, whitespace, and any non-alphanumeric chars except underscores
                val cleaned = value.trim().replace(Regex("^[\"']+|[\"']+$"), "").uppercase()
                valueOf(cleaned)
            } catch (e: IllegalArgumentException) {
                // Log in production: Unexpected role value: $value
                STUDENT
            }
        }
    }
}
