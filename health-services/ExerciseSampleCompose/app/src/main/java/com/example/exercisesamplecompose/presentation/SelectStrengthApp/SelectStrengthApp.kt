package com.example.exercisesamplecompose.presentation.SelectStrengthApp

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.scrollAway
import com.example.exercisesamplecompose.app.MainActivity
import com.example.exercisesamplecompose.presentation.component.BigChip
import com.example.exercisesamplecompose.presentation.component.MediumChip
import com.example.exercisesamplecompose.presentation.component.SmallChip
import com.example.exercisesamplecompose.presentation.component.TextSelectStrength
import com.example.exercisesamplecompose.presentation.theme.ExerciseSampleTheme


@Composable
fun SelectStrengthRoute(
    caseSelect: String?,
    onClick: (selectStrengthState) -> Unit
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
        SelectStrengthApp(select,onClick)
    }
}


@Composable
fun SelectStrengthApp(
    case: MainActivity.Case, //0:運動の記録　1:振動機能の使用
    onClick: (selectStrengthState) -> Unit,
    ) {
    ExerciseSampleTheme {
        // TODO: Swap to ScalingLazyListState
        val listState = rememberScalingLazyListState()

        /* *************************** Part 4: Wear OS Scaffold *************************** */
        // TODO (Start): Create a Scaffold (Wear Version)
        Scaffold(
            timeText = {
                TimeText(modifier = Modifier.scrollAway(listState))
            },
            vignette = {
                // Only show a Vignette for scrollable screens. This code lab only has one screen,
                // which is scrollable, so we show it all the time.
                Vignette(vignettePosition = VignettePosition.TopAndBottom)
            },
            positionIndicator = {
                PositionIndicator(
                    scalingLazyListState = listState
                )
            },


            ) {

            // Modifiers used by our Wear composables.
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
                autoCentering = AutoCenteringParams(itemIndex = 0),
                state = listState
            ) {

                /* ******************* Part 1: Simple composables ******************* */

                if (case == MainActivity.Case.REC){
                    item { TextSelectStrength(contentModifier, MainActivity.Case.REC) }
                    item{ BigChip(contentModifier, iconModifier, onClick ,
                        MainActivity.Case.REC) }
                    item{ MediumChip(contentModifier, iconModifier, {onClick},  MainActivity.Case.REC)}
                    item{ SmallChip(contentModifier, iconModifier, { onClick }, MainActivity.Case.REC)}
                }else if(case == MainActivity.Case.USE){
                    item { TextSelectStrength(contentModifier, MainActivity.Case.USE) }
                    item{ BigChip(contentModifier, iconModifier, { onClick },
                        MainActivity.Case.USE)}
                    item{ MediumChip(contentModifier, iconModifier, { onClick }, MainActivity.Case.USE)}
                    item{ SmallChip(contentModifier, iconModifier,
                        { onClick }, MainActivity.Case.USE)}
                }

            }

        }
    }
}
