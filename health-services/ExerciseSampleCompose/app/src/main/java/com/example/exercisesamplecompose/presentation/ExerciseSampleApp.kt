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
@file:OptIn(ExperimentalHorologistApi::class)

package com.example.exercisesamplecompose.presentation

import android.os.Vibrator
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.currentBackStackEntryAsState
import com.example.exercisesamplecompose.app.Screen
import com.example.exercisesamplecompose.app.Screen.Exercise
import com.example.exercisesamplecompose.app.Screen.ExerciseNotAvailable
import com.example.exercisesamplecompose.app.Screen.PreparingExercise
import com.example.exercisesamplecompose.app.Screen.Summary
import com.example.exercisesamplecompose.app.navigateToTopLevel
import com.example.exercisesamplecompose.database.RecordDao
import com.example.exercisesamplecompose.database.RecordRoomDatabase
import com.example.exercisesamplecompose.presentation.dialogs.ExerciseNotAvailable
import com.example.exercisesamplecompose.presentation.exercise.ExerciseRoute
import com.example.exercisesamplecompose.presentation.preparing.PreparingExerciseRoute
import com.example.exercisesamplecompose.presentation.summary.SummaryRoute
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.ambient.AmbientAware
import com.google.android.horologist.compose.ambient.AmbientState
import com.google.android.horologist.compose.navscaffold.WearNavScaffold
import com.google.android.horologist.compose.navscaffold.scrollable
import  com.example.exercisesamplecompose.presentation.FeedBackApp.FeedBackApp
import com.example.exercisesamplecompose.presentation.ImportHbDataApp.ImportHbDataApp
import com.example.exercisesamplecompose.presentation.mainmenu.MainMenu
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.SelectStrengthRoute
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.selectStrengthState
import com.example.exercisesamplecompose.presentation.history.HistoryScreen
import com.example.exercisesamplecompose.presentation.history.HistorySelect

/** Navigation for the exercise app. **/
@Composable
fun ExerciseSampleApp(
    navController: NavHostController,
    onFinishActivity: () -> Unit,
    viewModel:selectStrengthState,
    db:RecordRoomDatabase,
    dao:RecordDao,
    vibrator: Vibrator
) {

    val currentScreen by navController.currentBackStackEntryAsState()

    val isAlwaysOnScreen = currentScreen?.destination?.route in AlwaysOnRoutes

    AmbientAware(
        isAlwaysOnScreen = isAlwaysOnScreen
    ) { ambientStateUpdate ->

        WearNavScaffold(
            navController = navController,
            startDestination = Screen.MainMenu.route,
            timeText = {
                if (ambientStateUpdate.ambientState is AmbientState.Interactive) {
                    TimeText(
                        modifier = it,
                    )
                }
            }
        ) {
            composable(PreparingExercise.route+"/{caseStrength}/{caseSelect}") {
                PreparingExerciseRoute(

                    ambientState = ambientStateUpdate.ambientState,
                    onStart = {
                        navController.navigate(Exercise.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = false
                            }
                        }
                    },
                    onNoExerciseCapabilities = {
                        navController.navigate(ExerciseNotAvailable.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = false
                            }
                        }
                    },
                    onFinishActivity = onFinishActivity
                )
                Log.d("TAG", "${it.arguments?.getString("caseSelect")}  ${it.arguments?.getString("caseStrength")}}")
            }

            scrollable(Exercise.route) {
                ExerciseRoute(
                    ambientState = ambientStateUpdate.ambientState,
                    columnState = it.columnState,
                    onSummary = {
                        navController.navigateToTopLevel(Summary, Summary.buildRoute(it))
                    },
                    vibrator = vibrator
                )
            }

            composable(ExerciseNotAvailable.route) {
                ExerciseNotAvailable()
            }

            scrollable(Screen.HistorySelect.route){
                HistorySelect(onClick = {navController.navigate(Screen.HistoryScreen.route)},
                    selectStrengthState = viewModel,
                    columnState = it.columnState)
            }
            scrollable(Screen.HistoryScreen.route){
                HistoryScreen(columnState = it.columnState,
                    dao = dao,
                    selectStrengthState = viewModel,
                )
            }


            scrollable(
                Summary.route + "/{averageHeartRate}/{totalCalories}/{elapsedTime}/{minHeartRate}/{maxHeartRate}",
                arguments = listOf(
                    navArgument(Summary.averageHeartRateArg) { type = NavType.FloatType },
                    navArgument(Summary.totalCaloriesArg) { type = NavType.FloatType },
                    navArgument(Summary.elapsedTimeArg) { type = NavType.StringType },
                    navArgument(Summary.minHertRateArg) { type = NavType.FloatType},
                    navArgument(Summary.maxHertRateArg) { type = NavType.FloatType}
                )
            ) {
                SummaryRoute(
                    columnState = it.columnState,
                    onRestartClick = {
                        navController.navigateToTopLevel(Screen.MainMenu)
                    },
                    db = db,
                    dao = dao,
                    selectStrengthState = viewModel
                )
            }
            composable(Screen.FeedBack.route){
                FeedBackApp()
            }
            composable(Screen.ImportHbData.route){
                ImportHbDataApp()
            }
            scrollable(Screen.MainMenu.route){
                MainMenu(toRecordDataApp = {navController.navigate(Screen.SelectStrength.route+"/REC")},
                    toUseFunctionApp = {navController.navigate(Screen.SelectStrength.route+"/USE")},
                    toImportHbDataApp = {navController.navigate(Screen.ImportHbData.route)},
                    toHistorySelect = {navController.navigate(Screen.HistorySelect.route)},
                       columnState =  it.columnState)
            }
            scrollable(Screen.SelectStrength.route+ "/{caseSelect}"
                ){
                SelectStrengthRoute(
                    caseSelect = it.arguments?.getString("caseSelect"),
                    onClick ={navController.navigate(PreparingExercise.buildExerciseRoute(viewModel))},
                    selectStrengthState =  viewModel,
                    columnState = it.columnState
                )
            }


        }
    }
}

val AlwaysOnRoutes = listOf(PreparingExercise.route, Exercise.route)


