package com.example.exercisesamplecompose.presentation.SelectStrengthApp

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.example.exercisesamplecompose.app.MainActivity
import com.example.exercisesamplecompose.presentation.component.BigChip
import com.example.exercisesamplecompose.presentation.component.MediumChip
import com.example.exercisesamplecompose.presentation.component.SmallChip
import com.example.exercisesamplecompose.presentation.component.TextSelectStrength
import com.example.exercisesamplecompose.presentation.theme.ExerciseSampleTheme
import com.google.android.horologist.compose.layout.ScalingLazyColumnState


@Composable
fun SelectStrengthRoute(
    caseSelect: String?,
    onClick: () -> Unit,
    selectStrengthState: selectStrengthState,
    columnState: ScalingLazyColumnState
) {
    Log.d("TAG", "${caseSelect} ")
    var select:MainActivity.Case? = null

    if (caseSelect == "USE"){
        select = MainActivity.Case.USE
    }else if (caseSelect == "REC"){
        select = MainActivity.Case.REC
    }

    /*
    if (caseStrength == "BIG"){
        Strength = MainActivity.Case.BIG
    }else if (caseStrength == "MEDIUM"){
        Strength = MainActivity.Case.MEDIUM
    }else if (caseStrength == "SMALL"){
        Strength = MainActivity.Case.SMALL
    }
     */
    if (select != null) {
        SelectStrengthApp(select,onClick,selectStrengthState,columnState)
    }
}


@Composable
fun SelectStrengthApp(
    case: MainActivity.Case, //0:運動の記録　1:振動機能の使用
    onClick: () -> Unit,
    selectStrengthState: selectStrengthState,
    columnState: ScalingLazyColumnState
    ) {

    ExerciseSampleTheme {

            val contentModifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
            val iconModifier = Modifier
                .size(24.dp)
                .wrapContentSize(align = Alignment.Center)

            /* *************************** Part 3: ScalingLazyColumn *************************** */
            // TODO: Swap a ScalingLazyColumn (Wear's version of LazyColumn)
            ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            columnState = columnState
        ) {

            /* ******************* Part 1: Simple composables ******************* */

            if (case == MainActivity.Case.REC) {
                item { TextSelectStrength(contentModifier, MainActivity.Case.REC) }
                item {
                    BigChip(
                        contentModifier, iconModifier, onClick,
                        MainActivity.Case.REC, selectStrengthState
                    )
                }
                item {
                    MediumChip(
                        contentModifier,
                        iconModifier,
                        onClick,
                        MainActivity.Case.REC,
                        selectStrengthState
                    )
                }
                item {
                    SmallChip(
                        contentModifier,
                        iconModifier,
                        onClick,
                        MainActivity.Case.REC,
                        selectStrengthState
                    )
                }
            } else if (case == MainActivity.Case.USE) {
                item { TextSelectStrength(contentModifier, MainActivity.Case.USE) }
                item {
                    BigChip(
                        contentModifier, iconModifier, onClick,
                        MainActivity.Case.USE, selectStrengthState
                    )
                }
                item {
                    MediumChip(
                        contentModifier,
                        iconModifier,
                        onClick,
                        MainActivity.Case.USE,
                        selectStrengthState
                    )
                }
                item {
                    SmallChip(
                        contentModifier, iconModifier,
                        onClick, MainActivity.Case.USE, selectStrengthState
                    )
                }
            }
        }
    }
}
