package com.example.exercisesamplecompose.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Duration

@Entity(tableName = "setting_table")
data class Setting(
    @PrimaryKey
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "offset") val offset : Int,
    @ColumnInfo(name = "init") val init : Int,
    @ColumnInfo(name = "strength") val strength: Int,

)