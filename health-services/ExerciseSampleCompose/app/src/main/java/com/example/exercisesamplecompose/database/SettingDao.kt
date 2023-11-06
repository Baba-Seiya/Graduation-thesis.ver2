package com.example.exercisesamplecompose.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SettingDao {
    @Query("SELECT * FROM setting_table")
    suspend fun getAllSetting():Setting
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllSetting(setting:Setting)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSetting(setting:Setting)


}