package com.desktop.clock.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.desktop.clock.dialog.SettingDialog
import com.desktop.clock.states.GlobalState
import com.desktop.clock.system.HandUtils
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomePage() {
    val homeModifier = remember {
        if (GlobalState.isFullScreen.value && GlobalState.isTransparent.value) {
            Modifier.fillMaxSize().padding(bottom = HandUtils.getTaskbarHeight())
        } else Modifier.fillMaxSize().background(Color(41, 41, 50))
    }

    Box(
        modifier = homeModifier,
        contentAlignment = Alignment.Center
    ) {
        SettingDialog()
        CenterTimeBox()
        FloatingButton()
    }
}

@Composable
private fun CenterTimeBox() {
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }

    val date = Date(currentTime)
    var isOnTheHour by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontSize = if (GlobalState.showDateClock.value) 120.sp else 180.sp,
            color = Color.White,
            letterSpacing = 14.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                brush = Brush.linearGradient(
                    start = Offset.Zero,
                    end = Offset.Infinite,
                    colors = if (GlobalState.isGradualText.value) {
                        listOf(Color(255, 94, 158), Color(255, 185, 96))
                    } else listOf(Color(255, 255, 255), Color(255, 255, 255))
                )
            ),
            text = remember(date) {
                SimpleDateFormat("HH:mm:ss").format(date)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (GlobalState.showDateClock.value) Text(
            fontSize = 42.sp,
            color = Color.White,
            letterSpacing = 6.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                brush = Brush.linearGradient(
                    start = Offset.Zero,
                    end = Offset.Infinite,
                    colors = if (GlobalState.isGradualText.value) {
                        listOf(Color(66, 211, 146), Color(95, 212, 255))
                    } else listOf(Color(255, 255, 255), Color(255, 255, 255))
                )
            ),
            text = remember(date) {
                val format = SimpleDateFormat("M月d日 EEEE", Locale.CHINA)
                format.format(date)
            }
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            val newTime = System.currentTimeMillis()
            val newDate = Date(newTime)
            val calendar = Calendar.getInstance()
            calendar.time = newDate
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val isCurrentOnTheHour = calendar.get(Calendar.MINUTE) == 0 && calendar.get(Calendar.SECOND) == 0
            if (isCurrentOnTheHour && !isOnTheHour && GlobalState.isTellTheTime.value) HandUtils.halfHourOnTime(currentHour)
            isOnTheHour = isCurrentOnTheHour
            currentTime = newTime
        }
    }
}

@Composable
private fun FloatingButton() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .absoluteOffset(x = 0.dp, y = 0.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier
                .padding(40.dp)
                .clickable(
                    onClick = {
                        GlobalState.settingDialogState.value = true
                    }
                )
        ) {
            Icon(
                tint = Color.White,
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Filled.Settings,
                contentDescription = "Float Button Icon"
            )
        }
    }
}