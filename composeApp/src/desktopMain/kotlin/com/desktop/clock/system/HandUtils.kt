package com.desktop.clock.system

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Notification
import com.desktop.clock.states.GlobalState
import java.awt.Toolkit
import java.awt.Window
import java.util.*

object HandUtils {
    fun setDefaultLocale() {
        Locale.setDefault(Locale.CHINA)
    }

    fun getTaskbarHeight(): Dp {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val screenHeight = screenSize.height
        val maximizedWindowHeight = Window.getWindows().firstOrNull()?.bounds?.height ?: screenHeight
        return (screenHeight - maximizedWindowHeight).dp
    }

    fun halfHourOnTime(hour: Int) {
        val message = when (hour) {
            in 5..8 -> "早上好！"
            in 9..11 -> "上午好！"
            in 12..17 -> "下午好！"
            in 18..23 -> "晚上好！"
            else -> "深夜了，注意休息哦！"
        }

        GlobalState.trayState.value.sendNotification(
            Notification(
                title = "整点提醒",
                message = "现在是 $hour 点，$message",
                type = Notification.Type.Info
            )
        )
    }
}