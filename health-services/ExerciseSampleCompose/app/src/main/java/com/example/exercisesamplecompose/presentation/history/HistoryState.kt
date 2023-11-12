package com.example.exercisesamplecompose.presentation.history

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.exercisesamplecompose.app.MainActivity
import com.example.exercisesamplecompose.database.Record

class HistoryState(): ViewModel(){
    val data = mutableStateListOf<Record>()
    val bigAverage = mutableStateOf(60.0)
    val mediumAverage = mutableStateOf(60.0)
    val smallAverage = mutableStateOf(60.0)

}