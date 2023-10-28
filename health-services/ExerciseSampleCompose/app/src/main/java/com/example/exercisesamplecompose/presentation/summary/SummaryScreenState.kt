package com.example.exercisesamplecompose.presentation.summary

import java.time.Duration


data class SummaryScreenState(
    val averageHeartRate: Double,
    val minHeartRate: Double,
    val maxHeartRate: Double,
    val totalCalories: Double,
    val elapsedTime: Duration,
)