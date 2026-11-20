package com.example.cognitio.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// beszúráshoz: csak azok az oszlopok, amiket te adsz meg
@Serializable
data class TestResultInsert(
    @SerialName("test_type") val testType: String,
    val score: Double,
    @SerialName("test_date") val testDate: String   // "2026-11-20" formátum
)

// lekérdezéshez: minden oszlop
@Serializable
data class TestResult(
    val id: Long,
    @SerialName("created_at") val createdAt: String,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("test_type") val testType: String,
    val score: Double,
    @SerialName("test_date") val testDate: String
)