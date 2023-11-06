package com.example.exercisesamplecompose.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import com.example.exercisesamplecompose.presentation.SelectStrengthApp.selectStrengthState
import com.example.exercisesamplecompose.database.Record
import com.example.exercisesamplecompose.presentation.history.HistoryState
import java.text.DecimalFormat


@Composable
fun HistoryFormat(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    selectStrengthState: selectStrengthState,
    row: Record,
    content:HistoryState

) {
    val strength = selectStrengthState.caseStrength
    val select = selectStrengthState.caseSelect
    val df = DecimalFormat("#.##")

    Chip(
        modifier = modifier,
        onClick = {},
        label = {
                Text(
                    text = "${row.date.toString().drop(2)}" ,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

        },
        secondaryLabel = {
                         Text(
                             text = "Ave${df.format(row.averageHeartRate)} time${row.elapsedTime}",
                             maxLines = 1,
                             overflow = TextOverflow.Ellipsis
                         )
        }
    )


}


