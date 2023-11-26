package com.deathsdoor.chillback.common.ui.components.timepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


private const val hours = 24
private const val minutes = 60
private const val seconds = 60
private const val milliseconds = 100

@Composable
internal fun TimePicker(
    modifier: Modifier = Modifier,
    startTime : LocalTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
    showHours : Boolean = true,
    showMinutes : Boolean = true,
    showSeconds : Boolean = true,
    showMilliseconds : Boolean = false,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        Row {
           buildMap {
               if(showHours) put(hours,startTime.hour)
               if(showMinutes) put(minutes,startTime.minute)
               if(showSeconds) put(seconds,startTime.second)
               if(showMilliseconds) put(milliseconds,startTime.toMillisecondOfDay())
           }.forEach { entry ->
               Picker(count = entry.key,startIndex = entry.value){
                    Text(text = it.toString())
               }
           }
        }
    }
}

@Composable
private fun Picker(
    modifier: Modifier = Modifier,
    startIndex : Int = 0,
    count : Int,
    content: @Composable LazyItemScope.(index: Int) -> Unit,
){
    val lazyListState = rememberLazyListState(startIndex)
    LazyColumn(modifier = modifier, state = lazyListState){
        items(count = count,itemContent = content)
    }
}
