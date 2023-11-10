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
package com.example.exercisesamplecompose.presentation.component

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.DirectionsRun
import androidx.compose.material.icons.automirrored.rounded.DirectionsWalk
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.DirectionsWalk
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Message
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Publish
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.navArgument
import androidx.wear.compose.material.AppCard
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Switch
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.exercisesamplecompose.R
import com.example.exercisesamplecompose.app.MainActivity
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.selectStrengthState
import com.example.exercisesamplecompose.presentation.theme.ExerciseSampleTheme
import java.math.BigDecimal

/* Contains individual Wear OS demo composables for the code lab. */


@Composable
fun ButtonExample(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        // Button
        Button(
            modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
            onClick = { /* ... */ },
        ) {
            Icon(
                imageVector = Icons.Rounded.Phone,
                contentDescription = "triggers phone action",
                modifier = iconModifier
            )
        }
    }
}
@Composable
fun StartButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    case: MainActivity.Case,
    strength: MainActivity.Case
) {
    var aikon = Icons.AutoMirrored.Rounded.DirectionsRun

    if (strength == MainActivity.Case.BIG){
        aikon= Icons.AutoMirrored.Rounded.DirectionsRun
    }else if(strength == MainActivity.Case.MEDIUM){
        aikon = Icons.AutoMirrored.Rounded.DirectionsWalk
    }else if (strength == MainActivity.Case.SMALL){
        aikon = Icons.Rounded.RecordVoiceOver
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        // Button
        Button(
            modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
            onClick = { /* ... */ },
        ) {
            Icon(
                imageVector = aikon,
                contentDescription = "運動開始ボタン",
                modifier = iconModifier
            )
        }
    }
}

@Composable
fun TextExample(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = "Main Menu"
    )
}

@Composable
fun TextSelectStrength(modifier: Modifier = Modifier,case: MainActivity.Case) {
    if (case == MainActivity.Case.REC){
        Text(
            modifier = modifier,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            text = "運動の記録"
        )
    }else if(case == MainActivity.Case.USE){
        Text(
            modifier = modifier,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            text = "心拍機能の使用"
        )

    }
}
@Composable
fun TextStart(modifier: Modifier = Modifier, case: MainActivity.Case, strength: MainActivity.Case) {
    var moji:String? = null

    if (strength == MainActivity.Case.BIG){
        moji= "運動強度“強”\n"
    }else if(strength == MainActivity.Case.MEDIUM){
        moji = "運動強度”中”\n"
    }else if (strength == MainActivity.Case.SMALL){
        moji = "運動強度”弱”\n"
    }

    if (case == MainActivity.Case.REC){
        Text(
            modifier = modifier,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            text = "${moji}運動の記録を始めます"
        )
    }else if(case == MainActivity.Case.USE){
        Text(
            modifier = modifier,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            text = "${moji}心拍機能の使用を開始します"
        )

    }
}
@Composable
fun TextRecordData(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = "運動の記録"
    )
}
@Composable
fun TextImportData(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = "心拍データの取り込み"
    )
}
@Composable
fun TextFeedBack(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = "フィードバック"
    )
}



@Composable
fun CardExample(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier
) {
    AppCard(
        modifier = modifier,
        appImage = {
            Icon(
                imageVector = Icons.Rounded.Message,
                contentDescription = "triggers open message action",
                modifier = iconModifier

            )
        },
        appName = { Text("Messages") },
        time = { Text("12m") },
        title = { Text("Kim Green") },
        onClick = { /* ... */ }
    ) {
        Text("On my way!")
    }
}
@Composable
fun ChipExample(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier
) {

}

@Composable
fun ImportHbDataChip(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onNavigateToImportHbDataApp:() -> Unit
) {
    Chip(
        modifier = modifier,
        onClick = onNavigateToImportHbDataApp,
        label = {
            Text(
                text = "心拍データ取込",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Publish ,
                contentDescription = "心拍データの取り込みボタン",
                modifier = iconModifier
            )
        },
    )
}

@Composable
fun RecordDataChip(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onNavigateToRecordDataApp: () -> Unit
) {
    Chip(
        modifier = modifier,
        onClick = onNavigateToRecordDataApp,
        label = {
            Text(
                text = "運動の記録",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "運動の記録ボタン",
                modifier = iconModifier
            )
        },
    )
}

@Composable
fun UseFunctionChip(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onNavigateToUseFunctionApp:() -> Unit
) {
    Chip(
        modifier = modifier,
        onClick = {onNavigateToUseFunctionApp()},
        label = {
            Text(
                text = "振動機能の使用",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.DirectionsRun,
                contentDescription = "振動機能の使用",
                modifier = iconModifier
            )
        },
    )
}

@Composable
fun HistoryChip(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onNavigateToUseFunctionApp:() -> Unit
) {
    Chip(
        modifier = modifier,
        onClick = {onNavigateToUseFunctionApp()},
        label = {
            Text(
                text = "運動履歴",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.History,
                contentDescription = "記録の確認",
                modifier = iconModifier
            )
        },
    )
}

@Composable
fun SettingChip(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onNavigateToSetting:() -> Unit
) {
    Chip(
        modifier = modifier,
        onClick = {onNavigateToSetting()},
        label = {
            Text(
                text = "振動の設定",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Settings,
                contentDescription = "振動の設定",
                modifier = iconModifier
            )
        },
    )
}


@Composable
fun BigChip(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onNavigate:() -> Unit,
    case : MainActivity.Case,
    selectStrengthState: selectStrengthState
) {
    val strength = selectStrengthState.caseStrength
    val select = selectStrengthState.caseSelect
    var onClick  = {strength.value = MainActivity.Case.BIG
        select.value = case
        onNavigate()}
    if(case == MainActivity.Case.HISTORY ){
        onClick = {
            strength.value = MainActivity.Case.BIG
            select.value = case
            onNavigate()
        }
    }
    Chip(
        modifier = modifier,
        onClick = onClick,
        label = {
            Text(
                text = "運動強度大",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.DirectionsRun,
                contentDescription = "振動機能の使用",
                modifier = iconModifier
            )
        },
    )
}
@Composable
fun MediumChip(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onNavigate:() -> Unit,
    case: MainActivity.Case,
    selectStrengthState: selectStrengthState
) {

    val strength = selectStrengthState.caseStrength
    val select = selectStrengthState.caseSelect
    var onClick  = {strength.value = MainActivity.Case.MEDIUM
        select.value = case
        onNavigate()}
    if(case == MainActivity.Case.HISTORY ){
        onClick = {
            strength.value = MainActivity.Case.MEDIUM
            select.value = case
            onNavigate()
        }
    }
    Chip(
        modifier = modifier,
        onClick = onClick,
        label = {
            Text(
                text = "運動強度中",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.DirectionsWalk,
                contentDescription = "中",
                modifier = iconModifier
            )
        },
    )
}
@Composable
fun SmallChip(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onNavigate:() -> Unit,
    case: MainActivity.Case,
    selectStrengthState: selectStrengthState
) {
    val strength = selectStrengthState.caseStrength
    val select = selectStrengthState.caseSelect
    var onClick  = {strength.value = MainActivity.Case.SMALL
        select.value = case
        onNavigate()}
    if(case == MainActivity.Case.HISTORY ){
        onClick = {
            strength.value = MainActivity.Case.SMALL
            select.value = case
            onNavigate()
        }
    }

    Chip(
        modifier = modifier,
        onClick = onClick,
        label = {
            Text(
                text = "運動強度小",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.RecordVoiceOver,
                contentDescription = "小",
                modifier = iconModifier
            )
        },
    )
}


@Composable
fun ToggleChipExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }
    ToggleChip(
        modifier = modifier,
        checked = checked,
        toggleControl = {
            Switch(
                checked = checked,
                modifier = Modifier.semantics {
                    this.contentDescription = if (checked) "On" else "Off"
                }
            )
        },
        onCheckedChange = {
            checked = it
        },
        label = {
            Text(
                text = "Sound",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}
