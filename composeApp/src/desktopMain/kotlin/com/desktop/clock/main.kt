package com.desktop.clock

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import coil3.compose.AsyncImage
import com.desktop.clock.pages.HomePage
import com.desktop.clock.states.GlobalState
import com.desktop.clock.system.HandUtils
import desktop_clock.composeapp.generated.resources.Res
import desktop_clock.composeapp.generated.resources.favicon
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    val windowState = rememberWindowState(
        size = DpSize(1200.dp, 720.dp),
        placement = WindowPlacement.Maximized,
        position = WindowPosition(Alignment.Center),
    )

    if (GlobalState.isTellTheTime.value) Tray(
        tooltip = "Desktop Clock",
        state = GlobalState.trayState.value,
        icon = painterResource(Res.drawable.favicon)
    )

    if (GlobalState.isFullScreen.value) {
        Window(
            resizable = false,
            transparent = true,
            undecorated = true,
            state = windowState,
            title = "Desktop-Clock",
            onCloseRequest = ::exitApplication,
            icon = painterResource(Res.drawable.favicon)
        ) {
            Surface(
                color = Color.Transparent,
                modifier = Modifier.fillMaxSize()
            ) {
                HomeMain()
            }
        }
    } else Window(
        state = windowState,
        title = "Desktop-Clock",
        onCloseRequest = ::exitApplication,
        icon = painterResource(Res.drawable.favicon)
    ) {
        HomeMain()
    }

    LaunchedEffect(windowState) {
        HandUtils.setDefaultLocale()
    }
}

@Composable
private fun HomeMain() {
    MaterialTheme {
        HomePage()
    }
}