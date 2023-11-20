package com.example.exercisesamplecompose.service

import android.health.connect.datatypes.HeartRateRecord
import androidx.health.services.client.data.AggregateDataType
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.ExerciseState
import androidx.health.services.client.data.ExerciseUpdate.ActiveDurationCheckpoint
import androidx.health.services.client.data.LocationAvailability
import androidx.health.services.client.data.StatisticalDataPoint

data class ExerciseMetrics(
    val heartRate: Double? = null,
    val distance: Double? = null,
    val calories: Double? = null,
    val heartRateAverage: Double? = null,
    val maxHeartRate: Double? = null,
    val minHeartRate: Double? = null,
    val heartRateBpmStats :StatisticalDataPoint<Double>? = null
) {
    fun update(latestMetrics: DataPointContainer): ExerciseMetrics {
        return copy(
            heartRate = latestMetrics.getData(DataType.HEART_RATE_BPM).lastOrNull()?.value
                ?: heartRate,
            distance = latestMetrics.getData(DataType.DISTANCE_TOTAL)?.total ?: distance,
            calories = latestMetrics.getData(DataType.CALORIES_TOTAL)?.total ?: calories,
            heartRateAverage = latestMetrics.getData(DataType.HEART_RATE_BPM_STATS)?.average
                ?: heartRateAverage,
            maxHeartRate = latestMetrics.getData(DataType.HEART_RATE_BPM_STATS)?.max
                ?:maxHeartRate,
            minHeartRate = latestMetrics.getData(DataType.HEART_RATE_BPM_STATS)?.min
                ?:minHeartRate,
            heartRateBpmStats = latestMetrics.getData(DataType.HEART_RATE_BPM_STATS)
        )
    }
}

//Capturing most of the values associated with our exercise in a data class
data class ExerciseServiceState(
    val exerciseState: ExerciseState? = null,
    val exerciseMetrics: ExerciseMetrics = ExerciseMetrics(),
    val exerciseLaps: Int = 0,
    val activeDurationCheckpoint: ActiveDurationCheckpoint? = null,
    val locationAvailability: LocationAvailability = LocationAvailability.UNKNOWN
)