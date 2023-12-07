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

package com.example.exercisesamplecompose.presentation.summary

import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.AirplaneTicket
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Alert
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.example.exercisesamplecompose.R
import com.example.exercisesamplecompose.app.MainActivity
import com.example.exercisesamplecompose.database.Record
import com.example.exercisesamplecompose.database.RecordDao
import com.example.exercisesamplecompose.database.RecordRoomDatabase
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.selectStrengthState
import com.example.exercisesamplecompose.presentation.component.SummaryFormat
import com.example.exercisesamplecompose.presentation.component.formatCalories
import com.example.exercisesamplecompose.presentation.component.formatDistanceKm
import com.example.exercisesamplecompose.presentation.component.formatElapsedTime
import com.example.exercisesamplecompose.presentation.component.formatHeartRate
import com.example.exercisesamplecompose.presentation.theme.ThemePreview
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScalingLazyColumnState
import com.google.android.horologist.compose.material.AlertDialog
import com.google.android.horologist.compose.material.Chip
import com.google.android.horologist.compose.material.Confirmation
import com.google.android.horologist.compose.material.Icon
import com.google.android.horologist.compose.material.Title

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Double.NaN
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.Date
import kotlin.time.toKotlinDuration

/**End-of-workout summary screen**/
@RequiresApi(Build.VERSION_CODES.S)
@Composable
 fun SummaryRoute(
    onRestartClick: () -> Unit,
    columnState: ScalingLazyColumnState,
    db:RecordRoomDatabase,
    dao: RecordDao,
    selectStrengthState :selectStrengthState,
    vibrator: VibratorManager
) {
    val viewModel = hiltViewModel<SummaryViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SummaryScreen(uiState = uiState, onRestartClick = onRestartClick, columnState = columnState,db,dao,selectStrengthState,vibrator)
}


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SummaryScreen(
    uiState: SummaryScreenState,
    onRestartClick: () -> Unit,
    columnState: ScalingLazyColumnState,
    db:RecordRoomDatabase,
    dao: RecordDao,
    selectStrengthState :selectStrengthState,
    vibrator: VibratorManager
) {
    val df = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    val date = Date()
    val job = Job()
    val strength = selectStrengthState.caseStrength
    val time = formatElapsedTime(elapsedDuration = uiState.elapsedTime,true).text
    val record = Record(df.format(date),"${strength.value}",uiState.averageHeartRate,uiState.minHeartRate,uiState.maxHeartRate,uiState.totalCalories, time,selectStrengthState.caseSelect.value.str)
    vibrator.cancel()

    var showDialog by remember { mutableStateOf(false) }
    var showOkConfig by remember { mutableStateOf(false) }
    var showCancelConfig by remember { mutableStateOf(false) }

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        columnState = columnState
    ) {
        item { Title(text = stringResource(id = R.string.workout_complete)) }
        item {
            SummaryFormat(
                value = formatElapsedTime(uiState.elapsedTime, includeSeconds = true),
                metric = stringResource(id = R.string.duration),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            SummaryFormat(
                value = formatHeartRate(uiState.averageHeartRate),
                metric = stringResource(id = R.string.avgHR),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            SummaryFormat(
                value = formatHeartRate(uiState.maxHeartRate),
                metric = stringResource(id = R.string.maxHR),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            SummaryFormat(
                value = formatHeartRate(uiState.minHeartRate),
                metric = stringResource(id = R.string.minHR),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            SummaryFormat(
                value = formatCalories(uiState.totalCalories),
                metric = stringResource(id = R.string.calories),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Chip(
                label = stringResource(id = R.string.SAVE),
                onClick = {
                    if (selectStrengthState.caseSelect.value == MainActivity.Case.REC){
                        showDialog = true
                    }else{
                        CoroutineScope(Dispatchers.Main + job).launch {
                            Log.d("TAG", "insartALL ")
                            dao.insertAll(record)
                        }
                        onRestartClick()
                    }


                },
                modifier = Modifier
                    .padding(6.dp)
            )
        }
    }
    if (showDialog) {
        val liststate = rememberScalingLazyListState()
        AlertDialog(
            onCancelButtonClick = {

                showDialog = false
                showCancelConfig = true
            },
            onOKButtonClick = {
                CoroutineScope(Dispatchers.Main + job).launch {
                    Log.d("TAG", "insartALL ")
                    dao.insertAll(record)
                }
                showDialog = false
                showOkConfig = true
            },
            modifier = Modifier.fillMaxSize(),
            message = "記録中に緊張は感じませんでしたか？",
            title = "確認" ,
            scalingLazyListState = liststate,
            showDialog = true,
            okButtonContentDescription = "保存しました",
            cancelButtonContentDescription = "保存されません"


        )

    }
    if(showOkConfig){
        val liststate = rememberScalingLazyListState()
        Confirmation(
            onTimeout ={
                onRestartClick() } ,
            modifier = Modifier.fillMaxSize(),
            scrollState = liststate,
            content = {
                      Text(
                          text = "運動記録が保存されました",
                          textAlign = TextAlign.Center,
                          maxLines = 1,
                          overflow = TextOverflow.Visible,
                          fontSize = 12.sp
                          )
            },
            durationMillis = 3000,
            icon = {
                androidx.wear.compose.material.Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "丸ボタン",
                    modifier = Modifier.size(50.dp),
                    tint = Color(0,219,255)
                )
            },
        )
    }
    if (showCancelConfig){
        val liststate = rememberScalingLazyListState()
        Confirmation(
            onTimeout ={
                onRestartClick() } ,
            modifier = Modifier.fillMaxSize(),
            scrollState = liststate,
            content = {
                Text(
                    text = "条件を満たさなかった為保存されませんでした",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            },
            durationMillis = 3000,
            icon = {
                androidx.wear.compose.material.Icon(
                    imageVector = Icons.Rounded.Cancel,
                    contentDescription = "×ボタン",
                    modifier = Modifier.size(50.dp),
                    tint = Color(0,219,255)
                )
            },
        )
    }

}
/*


                    CoroutineScope(Dispatchers.Main + job).launch {
                        Log.d("TAG", "insartALL ")
                        dao.insertAll(record)
                    }
@Composable
fun CheckDialog(){
    var showDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScalingLazyListState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {



        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {


            Icon(
                Icons.Outlined.AirplaneTicket,
                contentDescription = "",
            )


        }


        androidx.wear.compose.material.dialog.Dialog(
            showDialog = showDialog,
            onDismissRequest = { showDialog = false }, scrollState = scrollState
        ) {

            Alert(
                scrollState = scrollState,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                contentPadding =
                PaddingValues(start = 10.dp, end = 10.dp, top = 24.dp, bottom = 52.dp),
                icon = {
                    Icon(
                        Icons.Outlined.Call,
                        contentDescription = "airplane",
                        modifier = Modifier
                            .size(24.dp)
                            .wrapContentSize(align = Alignment.Center)
                            .clickable { showDialog = false },

                        )
                },
                title = { Text(text = "Example Title Text", textAlign = TextAlign.Center) },
                message = {
                    Text(
                        text = "Message content goes here",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2
                    )
                },
            ) {
                item {
                    Chip(
                        label = Text("Primary").toString() ,
                        onClick = { showDialog = false },
                        colors = ChipDefaults.primaryChipColors(),
                    )
                }
                item {
                    Chip(
                        label = Text("Secondary").toString() ,
                        onClick = { showDialog = false },
                        colors = ChipDefaults.secondaryChipColors(),
                    )
                }


            }
        }
    }
}

@WearPreviewDevices
@Composable
fun SummaryScreenPreview() {
    ThemePreview {
        CheckDialog()
    }
}
*/