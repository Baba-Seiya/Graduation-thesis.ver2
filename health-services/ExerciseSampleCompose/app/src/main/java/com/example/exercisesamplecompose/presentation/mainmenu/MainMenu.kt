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
import com.example.exercisesamplecompose.presentation.component.HistoryChip
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.example.exercisesamplecompose.presentation.component.ImportHbDataChip
import com.example.exercisesamplecompose.presentation.component.RecordDataChip
import com.example.exercisesamplecompose.presentation.component.SettingChip
import com.example.exercisesamplecompose.presentation.component.UseFunctionChip
import com.example.exercisesamplecompose.presentation.theme.ExerciseSampleTheme
import com.google.android.horologist.compose.layout.ScalingLazyColumnState
import com.google.android.horologist.compose.material.Title


@Composable
fun MainMenu(
    toRecordDataApp:() -> Unit,
    toUseFunctionApp:() -> Unit,
    toImportHbDataApp:() -> Unit,
    toHistorySelect:() -> Unit,
    toSetting:() -> Unit,
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

            ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            columnState = columnState

        ) {
            /* ******************* Part 1: Simple composables ******************* */
            item { Title(text = stringResource(id = androidx.compose.ui.R.string.navigation_menu)) }
            /* ********************* Part 2: Wear unique composables ********************* */
            item {
                RecordDataChip(
                    contentModifier,
                    iconModifier,
                    onNavigateToRecordDataApp = { toRecordDataApp() })
            }
            item {
                UseFunctionChip(
                    contentModifier,
                    iconModifier,
                    onNavigateToUseFunctionApp = { toUseFunctionApp() })
            }

            item {
                HistoryChip (
                    contentModifier,
                    iconModifier,
                    onNavigateToUseFunctionApp = { toHistorySelect()}
                )

            }

            item{
                SettingChip(
                    contentModifier,
                    iconModifier,
                    onNavigateToSetting = {toSetting()}
                )
            }

            item {
                ImportHbDataChip(
                    contentModifier,
                    iconModifier,
                    onNavigateToImportHbDataApp = { toImportHbDataApp() })
            }


        }



    }
}

