/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.exercisesamplecompose.app

import android.util.Log
import androidx.navigation.NavController
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.selectStrengthState
import com.example.exercisesamplecompose.presentation.summary.SummaryScreenState

sealed class Screen(
    val route: String
) {
    object Exercise : Screen("exercise")
    object ExerciseNotAvailable : Screen("exerciseNotAvailable")
    object PreparingExercise : Screen("preparingExercise"){
        fun buildExerciseRoute(selectStrength: selectStrengthState):String{
            Log.d("ROUTE", "$route/${selectStrength.caseStrength.value}/${selectStrength.caseSelect.value}")
            return "$route/${selectStrength.caseStrength.value}/${selectStrength.caseSelect.value}"
        }
        val caseStrength = "caseStrength"
        val caseSelect = "caseSelect"
    }
    object Summary : Screen("summaryScreen") {
        fun buildRoute(summary: SummaryScreenState): String {
            return "$route/${summary.averageHeartRate}/${summary.totalCalories}/${summary.elapsedTime}/${summary.minHeartRate}/${summary.maxHeartRate}"
        }

        val averageHeartRateArg = "averageHeartRate"
        val minHertRateArg = "minHeartRate"
        val maxHertRateArg = "maxHeartRate"
        val totalCaloriesArg = "totalCalories"
        val elapsedTimeArg = "elapsedTime"
    }
    object FeedBack : Screen("feedback")
    object ImportHbData : Screen("importdata")
    object SelectStrength:Screen("selectstrength")
    object MainMenu:Screen("mainmenu")
    object HistorySelect:Screen("historySelect")
    object HistoryScreen:Screen("historyScreen"){
        fun buildHistoryScreenRoute(selectStrength: selectStrengthState):String{
            return "$route/${selectStrength.caseStrength.value}/"
        }
    }
}

fun NavController.navigateToTopLevel(screen: Screen, route: String = screen.route) {
    navigate(route) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}