package com.example.exercisesamplecompose.presentation.summary

import java.time.Duration


data class SummaryScreenState(
    val averageHeartRate: Double,
    val totalCalories: Double,
    val elapsedTime: Duration,
)