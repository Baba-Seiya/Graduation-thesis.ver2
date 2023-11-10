package com.example.exercisesamplecompose.presentation.SelectStrengthApp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.exercisesamplecompose.app.MainActivity

class selectStrengthState():ViewModel(){
    val caseStrength = mutableStateOf(MainActivity.Case.NULL)
    val caseSelect = mutableStateOf(MainActivity.Case.NULL)
    val vibrationJudge = mutableStateOf(false)

}
