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
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.DirectionsWalk
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Tune
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
import com.example.exercisesamplecompose.database.Setting
import com.example.exercisesamplecompose.database.SettingDao
import com.example.exercisesamplecompose.database.SettingRoomDatabase
import com.example.exercisesamplecompose.presentation.ExerciseSampleApp
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.selectStrengthState
import com.example.exercisesamplecompose.presentation.exercise.ExerciseViewModel
import com.example.exercisesamplecompose.presentation.history.HistoryState
import com.example.exercisesamplecompose.presentation.setting.SettingState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    companion object{
        lateinit var db :RecordRoomDatabase
        lateinit var dao : RecordDao
        lateinit var settingDB:SettingRoomDatabase
        lateinit var settingDao:SettingDao
    }
    private lateinit var navController: NavHostController
    private val exerciseViewModel by viewModels<ExerciseViewModel>()

    //APIレベル31以上のみ、wearOS 4以上が対応。（VIBRATOR_MANAGER_SERVICEを使用するため）
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        db = RecordRoomDatabase.getDatabase(this)
        dao = db.recordDao()

        settingDB = SettingRoomDatabase.getSettingDatabase(this)
        settingDao = settingDB.SettingDao()

        val vibrator = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager

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

        val SettingState: SettingState by lazy{
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
            ).get(SettingState::class.java)
        }

        val splash = installSplashScreen()
        var pendingNavigation = true

        splash.setKeepOnScreenCondition { pendingNavigation }

        super.onCreate(savedInstanceState)

        //常時表示する
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            navController = rememberSwipeDismissableNavController()

            ExerciseSampleApp(
                navController,
                onFinishActivity = { this.finish() },
                viewModel,
                historyState,
                SettingState,
                db,
                dao,
                vibrator,
                settingDao
            )

            LaunchedEffect(Unit) {
                prepareIfNoExercise()
                pendingNavigation = false
            }
        }
        //起動時にDBから設定内容を取り込む
        val job = Job()
        CoroutineScope(Dispatchers.Main + job).launch {
            val setting = Setting(1,0,60,30)
            settingDao.insertAllSetting(setting)

            Log.d("TAG", "onCreate:getAllSetting ")
            val settings = settingDao.getAllSetting()
            SettingState.init.value = settings.init
            SettingState.offset.value = settings.offset
            SettingState.strength.value = settings.strength
        }


    }
    enum class Case(val str:String,val icon:ImageVector){
        USE("USE", Icons.Rounded.DirectionsRun),
        REC("REC",Icons.Rounded.Edit),
        BIG("BIG",Icons.Rounded.DirectionsRun),
        MEDIUM("MEDIUM",Icons.Rounded.DirectionsWalk),
        SMALL("SMALL",Icons.Rounded.RecordVoiceOver),
        HISTORY("HISTORY",Icons.Rounded.Remove),
        CUSTOM("CUSTOM,",Icons.Rounded.Tune),
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



