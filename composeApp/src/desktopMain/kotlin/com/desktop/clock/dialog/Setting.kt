package com.desktop.clock.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.desktop.clock.states.GlobalState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun SettingDialog() {
    if (GlobalState.settingDialogState.value) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = { GlobalState.settingDialogState.value = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .requiredSizeIn(maxWidth = 600.dp, maxHeight = 400.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(25.dp)
                ) {
                    Text(
                        text = "设置 - 桌面时钟",
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn (
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        item { DialogContent() }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .absoluteOffset(x = 0.dp, y = 0.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 25.dp)
                            .fillMaxWidth(0.55f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SnackbarHost(
                            hostState = GlobalState.snackbarHostState.value
                        )
                    }
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                println(1)
                GlobalState.snackbarHostState.value.currentSnackbarData?.dismiss()
            }
        }
    }
}

@Composable
@OptIn(DelicateCoroutinesApi::class)
private fun DialogContent() {
    Column {
        DialogItem(
            name = "显示日期"
        ) {
            Switch(
                checked = GlobalState.showDateClock.value,
                onCheckedChange = {
                    GlobalState.showDateClock.value = !GlobalState.showDateClock.value
                }
            )
        }

        DialogItem(
            name = "窗口全屏"
        ) {
            Switch(
                checked = GlobalState.isFullScreen.value,
                onCheckedChange = {
                    GlobalScope.launch {
                        if (GlobalState.isTransparent.value) {
                            GlobalState.snackbarHostState.value.currentSnackbarData?.dismiss()
                            if (GlobalState.snackbarHostState.value.showSnackbar(
                                    actionLabel = "关闭透明",
                                    message = "请先关闭窗口透明",
                                ) == SnackbarResult.ActionPerformed) {
                                GlobalState.toggleTransparent()
                            }
                            return@launch
                        }
                        GlobalState.toggleFullScreen()
                    }
                }
            )
        }

        DialogItem(
            name = "窗口透明"
        ) {
            Switch(
                checked = GlobalState.isTransparent.value,
                onCheckedChange = {
                    GlobalScope.launch {
                        if (GlobalState.isFullScreen.value && !GlobalState.isTransparent.value) {
                            GlobalState.snackbarHostState.value.currentSnackbarData?.dismiss()
                            if (GlobalState.snackbarHostState.value.showSnackbar(
                                actionLabel = "关闭全屏",
                                message = "请先关闭窗口全屏",
                            ) == SnackbarResult.ActionPerformed) {
                                GlobalState.closeFullScreen()
                            }
                            return@launch
                        }
                        GlobalState.toggleTransparent()
                    }
                }
            )
        }

        DialogItem(
            name = "渐变文本"
        ) {
            Switch(
                checked = GlobalState.isGradualText.value,
                onCheckedChange = {
                    GlobalState.isGradualText.value = !GlobalState.isGradualText.value
                }
            )
        }

        DialogItem(
            name = "整点提醒"
        ) {
            Switch(
                checked = GlobalState.isTellTheTime.value,
                onCheckedChange = {
                    GlobalState.isTellTheTime.value = !GlobalState.isTellTheTime.value
                }
            )
        }
    }
}

@Composable
private fun DialogItem(
    name: String,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.width(15.dp))
        content()
    }
}