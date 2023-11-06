package com.example.exercisesamplecompose.presentation.setting

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberPickerState
import com.example.exercisesamplecompose.database.Setting
import com.example.exercisesamplecompose.database.SettingDao
import com.example.exercisesamplecompose.presentation.component.CaloriesText
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
    dao: SettingDao

) {
    val state = rememberPickerState(items.size)
    val job = Job()
    //TODO Pickerを完成させる
    Picker(
        state = state,
        contentDescription = "設定用Picker",
        onSelected = {
            thing.value = state.selectedOption
            CoroutineScope(Dispatchers.Main + job).launch {
                Log.d("TAG", "update")
                //TODO DBが実装できているか確認する　onCreateでSettingStateにDBから初期値を入れる処理を書く
                dao.updateSetting(setting = Setting(id = 1,offset = settingState.offset.value, init = settingState.init.value, strength = settingState.strength.value))
            }
         },
        modifier = modifier
            .fillMaxSize()
            .height(100.dp)
    ) {
        Text(text = items[it])
    }

}

@Composable
fun PickerExample() {
    val items = listOf("One", "Two", "Three", "Four", "Five")
    val state = rememberPickerState(items.size)
    Picker(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        state = state
    ) {
        Text(text = items[it])
    }
}
@Preview
@Composable
fun PickerExamplePreview() {
    PickerExample()
}
