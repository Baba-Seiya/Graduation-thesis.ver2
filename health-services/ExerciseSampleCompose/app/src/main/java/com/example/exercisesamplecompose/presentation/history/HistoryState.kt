package com.example.exercisesamplecompose.presentation.history

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.exercisesamplecompose.app.MainActivity
import com.example.exercisesamplecompose.database.Record

class HistoryState(): ViewModel(){
    val arraySize = mutableStateOf(0)
    val data = mutableStateListOf<Record>()
    val caseStrength = mutableStateOf(MainActivity.Case.NULL)
    val caseSelect = mutableStateOf(MainActivity.Case.NULL)
}