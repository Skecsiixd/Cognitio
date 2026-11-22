package com.example.cognitio.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TestResultInsert(
    @SerialName("test_type") val testType: String,
    val score: Double,
    @SerialName("test_date") val testDate: String
)

@Serializable
data class TestResult(
    val id: Long,
    @SerialName("created_at") val createdAt: String,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("test_type") val testType: String,
    val score: Double,
    @SerialName("test_date") val testDate: String
)