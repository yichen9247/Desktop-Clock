package com.desktop.clock.states

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.TrayState
import kotlinx.coroutines.delay

object GlobalState {
    val trayState: MutableState<TrayState> = mutableStateOf(
        TrayState()
    )

    val snackbarHostState: MutableState<SnackbarHostState> = mutableStateOf(
        SnackbarHostState()
    )

    val isFullScreen = mutableStateOf(false)
    val showDateClock = mutableStateOf(true)
    val isGradualText = mutableStateOf(false)
    val isTransparent = mutableStateOf(false)
    val isTellTheTime = mutableStateOf(false)
    val isImageBackground = mutableStateOf(false)
    val settingDialogState = mutableStateOf(false)

    suspend fun toggleTransparent() {
        if (!isTransparent.value && isFullScreen.value) return
        if (isTransparent.value) {
            isFullScreen.value = false
            isTransparent.value = false
        } else {
            delay(100)
            isFullScreen.value = true
            isTransparent.value = true
        }
        isImageBackground.value = false
        settingDialogState.value = false
    }

    fun toggleFullScreen() {
        if (isTransparent.value) return
        settingDialogState.value = false
        isFullScreen.value = !isFullScreen.value
    }

    fun closeFullScreen() {
        isFullScreen.value = false
        settingDialogState.value = false
    }
}