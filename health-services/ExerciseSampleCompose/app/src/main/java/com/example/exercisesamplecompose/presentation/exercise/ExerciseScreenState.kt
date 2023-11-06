package com.example.exercisesamplecompose.presentation.exercise

import com.example.exercisesamplecompose.data.ServiceState
import com.example.exercisesamplecompose.presentation.summary.SummaryScreenState
import com.example.exercisesamplecompose.service.ExerciseServiceState
import java.time.Duration

data class ExerciseScreenState(
    val hasExerciseCapabilities: Boolean,
    val isTrackingAnotherExercise: Boolean,
    val serviceState: ServiceState,
    val exerciseState: ExerciseServiceState?
) {
    fun toSummary(): SummaryScreenState {
        val exerciseMetrics = exerciseState?.exerciseMetrics
        val averageHeartRate = exerciseMetrics?.heartRateAverage ?: Double.NaN
        val minHeartRate = exerciseMetrics?.minHeartRate ?:Double.NaN
        val maxHeartRate =exerciseMetrics?.maxHeartRate ?:Double.NaN
        val totalCalories = exerciseMetrics?.calories ?: 0.0
        val duration = exerciseState?.activeDurationCheckpoint?.activeDuration ?: Duration.ZERO
        return SummaryScreenState(averageHeartRate, minHeartRate, maxHeartRate, totalCalories, duration)
    }

    val isEnding: Boolean
        get() = exerciseState?.exerciseState?.isEnding == true

    val isEnded: Boolean
        get() = exerciseState?.exerciseState?.isEnded == true

    val isPaused: Boolean
        get() = exerciseState?.exerciseState?.isPaused == true
}