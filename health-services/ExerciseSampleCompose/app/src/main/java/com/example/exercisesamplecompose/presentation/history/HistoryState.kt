package com.example.exercisesamplecompose.presentation.history

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.exercisesamplecompose.app.MainActivity

class HistoryState(): ViewModel(){
    val caseStrength = mutableStateOf(MainActivity.Case.NULL)
    val caseSelect = mutableStateOf(MainActivity.Case.NULL)
}