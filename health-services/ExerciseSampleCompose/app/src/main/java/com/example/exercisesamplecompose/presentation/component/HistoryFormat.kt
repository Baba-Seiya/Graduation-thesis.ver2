package com.example.exercisesamplecompose.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.magnifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.selectStrengthState
import com.example.exercisesamplecompose.presentation.theme.ExerciseSampleTheme
import com.google.android.horologist.compose.layout.ScalingLazyColumnState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.exercisesamplecompose.R
import com.example.exercisesamplecompose.app.MainActivity
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.material.Title




@Composable
fun HistoryFormat(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    selectStrengthState: selectStrengthState

) {
    val strength = selectStrengthState.caseStrength
    val select = selectStrengthState.caseSelect
    Column {
        Chip(
            modifier = modifier,
            onClick = {},
            label = {
                    Text(
                        text = "" ,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

            },
            icon = {
                Icon(imageVector = strength.value.icon,
                    contentDescription = "",
                    modifier = iconModifier
                )

            }
        )
    }

}


