package com.example.exercisesamplecompose.presentation.SelectStrengthApp

import android.util.Log
import com.example.exercisesamplecompose.app.MainActivity
import com.example.exercisesamplecompose.app.Screen
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.selectStrengthState

fun registSelectStrengthState(
    case:MainActivity.Case,
    strength:MainActivity.Case,
    onNavigate:(selectStrengthState)->Unit,
    uiState:selectStrengthState){
        selectStrengthState(
            caseSelect = case.str,
            caseStrength =strength.str
        )
    Log.d("TAG", "  ${uiState.caseSelect} ${uiState.caseStrength} ")
        onNavigate
}