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

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.DirectionsWalk
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.room.Room
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.exercisesamplecompose.database.RecordDao
import com.example.exercisesamplecompose.database.RecordRoomDatabase
import com.example.exercisesamplecompose.presentation.ExerciseSampleApp
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.selectStrengthState
import com.example.exercisesamplecompose.presentation.exercise.ExerciseViewModel
import com.example.exercisesamplecompose.presentation.history.HistoryState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    companion object{
        lateinit var db :RecordRoomDatabase
        lateinit var dao : RecordDao
    }
    private lateinit var navController: NavHostController
    private val exerciseViewModel by viewModels<ExerciseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        db = RecordRoomDatabase.getDatabase(this)
        dao = db.recordDao()
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val viewModel: selectStrengthState by lazy{
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
            ).get(selectStrengthState::class.java)
        }
        val historyState: HistoryState by lazy{
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
            ).get(HistoryState::class.java)
        }
        val splash = installSplashScreen()
        var pendingNavigation = true

        splash.setKeepOnScreenCondition { pendingNavigation }

        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberSwipeDismissableNavController()

            ExerciseSampleApp(
                navController,
                onFinishActivity = { this.finish() },
                viewModel,
                historyState,
                db,
                dao,
                vibrator
            )

            LaunchedEffect(Unit) {
                prepareIfNoExercise()
                pendingNavigation = false
            }
        }
    }
    enum class Case(val str:String,val icon:ImageVector){
        USE("USE", Icons.Rounded.DirectionsRun),
        REC("REC",Icons.Rounded.Edit),
        BIG("BIG",Icons.Rounded.DirectionsRun),
        MEDIUM("MEDIUM",Icons.Rounded.DirectionsWalk),
        SMALL("SMALL",Icons.Rounded.RecordVoiceOver),
        HISTORY("HISTORY",Icons.Rounded.Remove),
        NULL("NULL",Icons.Rounded.Remove)
    }
    private suspend fun prepareIfNoExercise() {
        /** Check if we have an active exercise. If true, set our destination as the
         * Exercise Screen. If false, route to preparing a new exercise. **/
        val isRegularLaunch =
            navController.currentDestination?.route == Screen.Exercise.route
        if (isRegularLaunch && !exerciseViewModel.isExerciseInProgress()) {
            navController.navigate(Screen.PreparingExercise.route)
        }
    }
}



