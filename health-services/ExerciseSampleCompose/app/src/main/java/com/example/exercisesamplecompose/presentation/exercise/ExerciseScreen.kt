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

package com.example.exercisesamplecompose.presentation.exercise

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.filled._360
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.exercisesamplecompose.R
import com.example.exercisesamplecompose.app.MainActivity
import com.example.exercisesamplecompose.database.Setting
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.selectStrengthState
import com.example.exercisesamplecompose.presentation.component.CaloriesText
import com.example.exercisesamplecompose.presentation.component.DistanceText
import com.example.exercisesamplecompose.presentation.component.HRText
import com.example.exercisesamplecompose.presentation.component.PauseButton
import com.example.exercisesamplecompose.presentation.component.ResumeButton
import com.example.exercisesamplecompose.presentation.component.StartButton
import com.example.exercisesamplecompose.presentation.component.StopButton
import com.example.exercisesamplecompose.presentation.component.formatElapsedTime
import com.example.exercisesamplecompose.presentation.history.HistoryState
import com.example.exercisesamplecompose.presentation.setting.SettingState
import com.example.exercisesamplecompose.presentation.summary.SummaryScreenState
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.ambient.AmbientState
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnState
import com.google.android.horologist.health.composables.ActiveDurationText

@RequiresApi(Build.VERSION_CODES.S)
fun sendHeartRateNotification(
    heartRate: Double,
    vibrator:VibratorManager,
    vibrationJudge: MutableState<Boolean>,
    historyState: HistoryState,
    selectStrengthState: selectStrengthState,
    setting: SettingState
) {
    //バイブレーションのさせ方を記述する。
    val strength = selectStrengthState.caseStrength.value
    val vibrationStrength = setting.strength.value
    var judgeRate : Double = 0.0

    //心拍基準を決める処理
    if(strength == MainActivity.Case.BIG){
        if (historyState.bigAverage.value == 0.0){
            historyState.bigAverage.value = setting.init.value.toDouble()
        }
        judgeRate = historyState.bigAverage.value + setting.offset.value.toDouble()
    }else if (strength == MainActivity.Case.MEDIUM){
        judgeRate = setting.init.value.toDouble() + setting.offset.value.toDouble()
    }else if (strength == MainActivity.Case.SMALL){
        if (historyState.smallAverage.value == 0.0){
            historyState.smallAverage.value = setting.init.value.toDouble()
        }
        judgeRate = historyState.smallAverage.value + setting.offset.value.toDouble()
    }

    //BPMを振動に変換する処理(現状基準値と同じBPMを作る)
    val bpm = judgeRate * 0.8//8割遅くしたBPMが入る
    val ms = 60 / bpm * 1000 //ミリ秒に変換(BPMはDoubleじゃないと計算できない)


    //心拍が基準を超えているか確認する処理
    if (heartRate >= judgeRate) {
        Log.d("振動 ", "検知しました")
        if(!vibrationJudge.value) {
            val vibrationEffect =
                VibrationEffect.createWaveform(longArrayOf(ms.toLong()-100L, 100), intArrayOf(0, vibrationStrength), 0)
            val combinedVibration = CombinedVibration.createParallel(vibrationEffect)
            vibrator.vibrate(combinedVibration)
            vibrationJudge.value = true
            Log.d("振動 ", "バイブレーションを開始しました。間隔は${ms.toLong()-100+100}")
        }
    }else{
        if(vibrationJudge.value){
            vibrator.cancel()
            vibrationJudge.value = false
            Log.d("振動 ", "振動をオフにしました")
        }
    }
}
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ExerciseRoute(
    ambientState: AmbientState,
    columnState: ScalingLazyColumnState,
    modifier: Modifier = Modifier,
    onSummary: (SummaryScreenState) -> Unit,
    vibrator:VibratorManager,
    selectStrengthState:selectStrengthState,
    historyState: HistoryState,
    setting: SettingState
) {
    val viewModel = hiltViewModel<ExerciseViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val strength = selectStrengthState.caseSelect

    // 心拍数データの取得
    val heartRate = uiState.exerciseState?.exerciseMetrics?.heartRate

// 心拍数が取得された場合、通知を送信するメソッドを呼び出す
    if (heartRate != null) {
        if(strength.value == MainActivity.Case.USE){
            sendHeartRateNotification(heartRate,vibrator,selectStrengthState.vibrationJudge,historyState,selectStrengthState,setting)
        }
    }
    if (uiState.isEnded) {
        SideEffect {
            onSummary(uiState.toSummary())
        }
    }

    if (ambientState is AmbientState.Interactive) {
        ExerciseScreen(
            onPauseClick = { viewModel.pauseExercise() },
            onEndClick = { viewModel.endExercise() },
            onResumeClick = { viewModel.resumeExercise() },
            onStartClick = { viewModel.startExercise() },
            uiState = uiState,
            columnState = columnState,
            modifier = modifier
        )
    }
}

/**
 * Shows while an exercise is in progress
 */
@Composable
fun ExerciseScreen(
    onPauseClick: () -> Unit,
    onEndClick: () -> Unit,
    onResumeClick: () -> Unit,
    onStartClick: () -> Unit,
    uiState: ExerciseScreenState,
    columnState: ScalingLazyColumnState,
    modifier: Modifier = Modifier
) {
    ScalingLazyColumn(
        modifier = modifier.fillMaxSize(),
        columnState = columnState
    ) {
        item {
            DurationRow(uiState)
        }

        item {
            HeartRateAndCaloriesRow(uiState)
        }

        item {
            ExerciseControlButtons(uiState, onStartClick, onEndClick, onResumeClick, onPauseClick)
        }
    }
}

@Composable
private fun ExerciseControlButtons(
    uiState: ExerciseScreenState,
    onStartClick: () -> Unit,
    onEndClick: () -> Unit,
    onResumeClick: () -> Unit,
    onPauseClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (uiState.isEnding) {
            StartButton(onStartClick)
        } else {
            StopButton(onEndClick)
        }

        if (uiState.isPaused) {
            ResumeButton(onResumeClick)
        } else {
            PauseButton(onPauseClick)
        }
    }
}



@Composable
private fun HeartRateAndCaloriesRow(uiState: ExerciseScreenState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Row {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = stringResource(id = R.string.heart_rate)
            )
            HRText(
                hr = uiState.exerciseState?.exerciseMetrics?.heartRate
            )
        }
        Row {
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = stringResource(id = R.string.calories)
            )
            CaloriesText(
                uiState.exerciseState?.exerciseMetrics?.calories
            )
        }
    }
}

@Composable
private fun DurationRow(uiState: ExerciseScreenState) {
    val lastActiveDurationCheckpoint = uiState.exerciseState?.activeDurationCheckpoint
    val exerciseState = uiState.exerciseState?.exerciseState
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.WatchLater,
                contentDescription = stringResource(id = R.string.duration)
            )
            if (exerciseState != null && lastActiveDurationCheckpoint != null) {
                ActiveDurationText(
                    checkpoint = lastActiveDurationCheckpoint,
                    state = uiState.exerciseState.exerciseState
                ) {
                    Text(text = formatElapsedTime(it, includeSeconds = true))
                }
            } else {
                Text(text = "--")
            }
        }
    }
}








