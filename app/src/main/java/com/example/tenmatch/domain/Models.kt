package com.example.tenmatch.domain

/**
 * Fundamental domain entities for the application.
 * Using data classes to leverage copy() for State Hoisting.
 */

data class Location(
    val latitude: Double,
    val longitude: Double,
    val address: String
)

enum class UserRole {
    ADMIN, USER, GUEST
}

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val city: String
)

enum class ReportStatus {
    PENDING, RESOLVED, REJECTED
}

data class Report(
    val id: String,
    val title: String,
    val location: Location,
    val status: ReportStatus,
    val creatorId: String
)
