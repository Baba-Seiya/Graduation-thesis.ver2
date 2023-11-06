package com.example.exercisesamplecompose.presentation.setting

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.exercisesamplecompose.app.MainActivity

class SettingState():ViewModel(){
    val offset = mutableStateOf(0)
    val init = mutableStateOf(60)
    val strength = mutableStateOf(30)
    val route = mutableStateOf("offset")
}
