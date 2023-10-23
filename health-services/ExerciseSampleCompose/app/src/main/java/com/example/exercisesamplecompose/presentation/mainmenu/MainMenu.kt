/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.exercisesamplecompose.presentation.mainmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.scrollAway
import com.example.exercisesamplecompose.R
import com.example.exercisesamplecompose.presentation.component.ImportHbDataChip
import com.example.exercisesamplecompose.presentation.component.RecordDataChip
import com.example.exercisesamplecompose.presentation.component.TextExample
import com.example.exercisesamplecompose.presentation.component.UseFunctionChip
import com.example.exercisesamplecompose.presentation.theme.ExerciseSampleTheme
import com.google.android.horologist.compose.material.Title


@Composable
fun MainMenu(
    toRecordDataApp:() -> Unit,
    toUseFunctionApp:() -> Unit,
    toImportHbDataApp:() -> Unit) {
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
                item { Title(text = stringResource(id = androidx.compose.ui.R.string.navigation_menu))  }
                /* ********************* Part 2: Wear unique composables ********************* */
                item { RecordDataChip(contentModifier, iconModifier,onNavigateToRecordDataApp = {toRecordDataApp()})}
                item { UseFunctionChip(contentModifier, iconModifier,onNavigateToUseFunctionApp = {toUseFunctionApp()})}
                item { ImportHbDataChip(contentModifier, iconModifier,onNavigateToImportHbDataApp = {toImportHbDataApp()}) }


            }


        }
    }
}

