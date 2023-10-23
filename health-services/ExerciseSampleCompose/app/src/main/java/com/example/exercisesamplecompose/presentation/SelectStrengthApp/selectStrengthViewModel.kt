package com.example.exercisesamplecompose.presentation.SelectStrengthApp

import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.exercisesamplecompose.app.Screen
import com.example.exercisesamplecompose.presentation.summary.SummaryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.Duration
import javax.inject.Inject
@HiltViewModel
class selectStrengthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
):ViewModel(){
    val uiState = MutableStateFlow(
        selectStrengthState(
            caseStrength = savedStateHandle.get<String>(Screen.PreparingExercise.caseStrength)!!,
            caseSelect = savedStateHandle.get<String>(Screen.PreparingExercise.caseSelect)!!
        )
    )
}
