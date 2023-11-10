package com.example.exercisesamplecompose.presentation.setting

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonColors
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberPickerState
import com.example.exercisesamplecompose.R
import com.example.exercisesamplecompose.database.Setting
import com.example.exercisesamplecompose.database.SettingDao
import com.example.exercisesamplecompose.presentation.component.CaloriesText
import com.example.exercisesamplecompose.presentation.component.HistoryChip
import com.example.exercisesamplecompose.presentation.component.ImportHbDataChip
import com.example.exercisesamplecompose.presentation.component.RecordDataChip
import com.example.exercisesamplecompose.presentation.component.SettingChip
import com.example.exercisesamplecompose.presentation.component.UseFunctionChip
import com.example.exercisesamplecompose.presentation.theme.ExerciseSampleTheme
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.material.Title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun SettingFormat(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    items :List<String>,
    thing: MutableState<Int>,
    settingState: SettingState,
    dao: SettingDao,
    navigateBack : () -> Unit

) {
    val state = rememberPickerState(items.size)
    val job = Job()
    val contentDescription = "設定用Picker"
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier
                .align(CenterHorizontally)
                .padding(top = 10.dp),
            text = "Selected: ${items[state.selectedOption]}"
        )
        Picker(
            modifier = modifier
                .align(CenterHorizontally)
                .size(100.dp, 100.dp),
            state = state,
            contentDescription = contentDescription,
        ) {
            Text(items[it])
        }
        com.google.android.horologist.compose.material.Button(
            imageVector = Icons.Default.Check,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Green,
                contentColor = Color.White
            ),
            contentDescription = "決定ボタン",
            onClick = {
                thing.value = items[state.selectedOption].toInt()
                CoroutineScope(Dispatchers.Main + job).launch {
                    Log.d("TAG", "update")
                    //TODO DBが実装できているか確認する　onCreateでSettingStateにDBから初期値を入れる処理を書く
                    dao.updateSetting(
                        setting = Setting(
                            id = 1,
                            offset = settingState.offset.value,
                            init = settingState.init.value,
                            strength = settingState.strength.value
                        )
                    )
                }
                navigateBack()
            }
        )
    }
}


@Composable
fun PickerExample() {
    val items = listOf("One", "Two", "Three", "Four", "Five")
    val state = rememberPickerState(items.size)
    val contentDescription = "kamesam"
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 10.dp),
            text = "Selected: ${items[state.selectedOption]}"
        )
        Picker(
            modifier = Modifier
                .align(CenterHorizontally)
                .size(100.dp, 100.dp),
            state = state,
            contentDescription = contentDescription,
        ) {
            Text(items[it])
        }
        com.google.android.horologist.compose.material.Button(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = stringResource(id = R.string.start_button_cd),
            onClick = {}
        )


    }

}
@Preview
@Composable
fun PickerExamplePreview() {
    PickerExample()
}
