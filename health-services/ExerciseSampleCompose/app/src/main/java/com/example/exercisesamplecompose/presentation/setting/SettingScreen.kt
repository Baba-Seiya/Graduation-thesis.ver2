package com.example.exercisesamplecompose.presentation.setting

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.example.exercisesamplecompose.R
import com.example.exercisesamplecompose.app.MainActivity
import com.example.exercisesamplecompose.database.RecordDao
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.selectStrengthState
import com.example.exercisesamplecompose.presentation.component.BigChip
import com.example.exercisesamplecompose.presentation.component.HistoryFormat
import com.example.exercisesamplecompose.presentation.component.MediumChip
import com.example.exercisesamplecompose.presentation.component.SmallChip
import com.example.exercisesamplecompose.presentation.theme.ExerciseSampleTheme
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnState
import com.google.android.horologist.compose.material.Title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import androidx.wear.compose.material.Chip

@Composable
fun SettingScreen(
    columnState: ScalingLazyColumnState,
    dao: RecordDao,
    settingState: SettingState,
    navigateToSettingFormat:() -> Unit,
) {
    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        columnState = columnState
    ){
        item{ Title(text = stringResource(id = R.string.setting_title)) }

        item{
            Chip(
                label = {
                        Text(
                            text = "振動の強さ",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                },
                secondaryLabel ={
                    Text(
                        text = "${settingState.strength.value}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                ) },
                onClick ={
                        settingState.route.value = "strength"
                    navigateToSettingFormat()
                },
                modifier =Modifier.fillMaxSize()

            )
        }

        item{
            Chip(
                label = {
                    Text(
                        text = "振動の初期値",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                secondaryLabel ={
                    Text(
                        text = "${settingState.init.value}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    ) },
                onClick ={
                         settingState.route.value = "init"
                    navigateToSettingFormat()
                },
                modifier =Modifier.fillMaxSize()

            )
        }
        item{
            Chip(
                label = {
                    Text(
                        text = "振動開始心拍数の初期値",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                secondaryLabel ={
                    Text(
                        text = "${settingState.offset.value}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    ) },
                onClick ={
                         settingState.route.value = "offset"
                    navigateToSettingFormat()
                },
                modifier =Modifier.fillMaxSize()

            )
        }
    }
}