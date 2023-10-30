package com.example.exercisesamplecompose.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Duration

@Entity(tableName = "record_table")
data class Record(
    @PrimaryKey
    @ColumnInfo(name = "strength") val strength : String,
    @ColumnInfo(name = "averageHeartRate") val averageHeartRate: Double,
    @ColumnInfo(name = "minHeartRate") val minHeartRate :Double,
    @ColumnInfo(name = "maxHeartRate") val maxHeartRate :Double,
    @ColumnInfo(name = "totalCalories") val totalCalories :Double,
    @ColumnInfo(name = "elapsedTime") val elapsedTime :String
)