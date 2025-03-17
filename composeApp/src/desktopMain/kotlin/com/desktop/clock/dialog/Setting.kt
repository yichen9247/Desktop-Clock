package com.desktop.clock.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
                        .padding(20.dp)
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
                    GlobalState.toggleFullScreen()
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