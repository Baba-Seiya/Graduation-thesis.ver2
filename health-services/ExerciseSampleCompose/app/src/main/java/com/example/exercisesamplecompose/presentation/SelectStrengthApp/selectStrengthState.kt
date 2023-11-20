package com.example.exercisesamplecompose.presentation.SelectStrengthApp

import androidx.compose.runtime.mutableStateOf
import androidx.health.services.client.data.StatisticalDataPoint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.exercisesamplecompose.app.MainActivity

class selectStrengthState(
    savedStateHandle: SavedStateHandle
):ViewModel(){
    val caseStrength = mutableStateOf(MainActivity.Case.NULL)
    val caseSelect = mutableStateOf(MainActivity.Case.NULL)
    val vibrationJudge = mutableStateOf(false)
    val heartRateBpmStats  = mutableListOf<Any>()
}
