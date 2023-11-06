package com.example.exercisesamplecompose.database

import android.telephony.CellSignalStrength
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Query("SELECT * FROM record_table")
    suspend fun getAll():Record
    @Query("SELECT * FROM record_table WHERE strength IN (:strength)")
    suspend fun getStrength(strength: String):List<Record>
    @Insert
    suspend fun insertAll(record:Record)

}