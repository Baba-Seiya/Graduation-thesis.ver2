package com.example.exercisesamplecompose.presentation.history

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
import androidx.compose.ui.unit.dp
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
import com.google.android.horologist.compose.material.Chip


@Composable
fun HistorySelect(
    onClick: () -> Unit,
    selectStrengthState: selectStrengthState,
    historyState: HistoryState,
    columnState: ScalingLazyColumnState
) {
    ExerciseSampleTheme {

        val contentModifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
        val iconModifier = Modifier
            .size(24.dp)
            .wrapContentSize(align = Alignment.Center)

        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            columnState = columnState
        ) {

            item { Title(text = stringResource(id = R.string.title)) }
            item {
                BigChip(
                    contentModifier, iconModifier, onClick,
                    MainActivity.Case.HISTORY, selectStrengthState
                )
            }
            item {
                MediumChip(
                    contentModifier,
                    iconModifier,
                    onClick,
                    MainActivity.Case.HISTORY,
                    selectStrengthState
                )
            }
            item {
                SmallChip(
                    contentModifier,
                    iconModifier,
                    onClick,
                    MainActivity.Case.HISTORY,
                    selectStrengthState
                )
            }

        }
    }
}





@Composable
fun HistoryScreen(
    columnState: ScalingLazyColumnState,
    dao: RecordDao,
    selectStrengthState : selectStrengthState,
    historyState: HistoryState
) {
    val case = selectStrengthState.caseStrength

    val job = Job()



    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        columnState = columnState
    ){
        item{ Title(text = stringResource(id = R.string.title)) }

        for(i in historyState.data){
            item{
                HistoryFormat(
                    selectStrengthState = selectStrengthState,
                    content = historyState,
                    row = i,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }


        item{
            Chip(
                label = stringResource(id = R.string.kousin),
                onClick = {
                    CoroutineScope(Dispatchers.Main+job).launch {
                        val data = dao.getStrength(case.value.str)
                        historyState.data.clear()
                        for (i in data){
                            historyState.data.add(i)
                        }
                        Log.d("DataBase", "${data.toString()}")

                    }
                          },
                modifier =Modifier.padding(6.dp)

            )
        }
    }
}