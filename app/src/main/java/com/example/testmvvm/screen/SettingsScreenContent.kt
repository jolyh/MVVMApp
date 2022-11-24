package com.example.testmvvm.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.testmvvm.viewModel.SettingsViewModel
import com.example.testmvvm.viewModel.UIEvent
import com.example.testmvvm.viewModel.UIState
import com.example.testmvvm.model.User

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val uiState = viewModel._uiState

    Scaffold(
        floatingActionButton = {
            Fab {
                viewModel.onEvent(UIEvent.SubmitUser)
                viewModel.onEvent(UIEvent.SubmitSettings)
            }
        }
    ) { it ->
        Column(modifier = Modifier
            .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            UserContainer(
                uiState = uiState,
                onNameChanged = { viewModel.onEvent(UIEvent.UserNameChanged(it)) },
                onEmailChanged = { viewModel.onEvent(UIEvent.UserEmailChanged(it)) }
            )

            SettingContainer(
                uiState = uiState,
                onChannelKeyChanged = { viewModel.onEvent(UIEvent.ChannelKeyChanged(it)) },
                onPushChanged = { viewModel.onEvent(UIEvent.PushEnabledChanged(it)) }
            )

        }
    }
}

@Composable
fun Fab(fabOnClick : () -> Unit) {
    FloatingActionButton(onClick = { fabOnClick() }) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = "fab")
    }
}

@Composable
fun UserContainer(
    uiState: MutableState<UIState>,
    onNameChanged : (String) -> Unit,
    onEmailChanged : (String) -> Unit,
) {
    UserInput(uiState = uiState,
        onNameChanged = onNameChanged,
        onEmailChanged = onEmailChanged)
}

@Composable
fun SettingContainer(
    uiState: MutableState<UIState>,
    onChannelKeyChanged : (String) -> Unit,
    onPushChanged : (Boolean) -> Unit,
){
    SettingsInput(uiState = uiState,
        onChannelKeyChanged = onChannelKeyChanged,
        onPushChanged = onPushChanged)
}

@Composable
fun UserInput(
    uiState: MutableState<UIState>,
    onNameChanged : (String) -> Unit,
    onEmailChanged : (String) -> Unit,
) {

    OutlinedTextField(
        label = { Text(text = "NAME") },
        value = uiState.value.userName,
        onValueChange = {
            onNameChanged(it)
        },
        isError = uiState.value.hasNameError
    )
    OutlinedTextField(
        label = { Text(text = "EMAIL") },
        value =  uiState.value.userEmail,
        onValueChange = {
            onEmailChanged(it)
        },
        isError = uiState.value.hasEmailError
    )

}

@Composable
fun SettingsInput(
    uiState: MutableState<UIState>,
    onChannelKeyChanged : (String) -> Unit,
    onPushChanged : (Boolean) -> Unit,
) {

    OutlinedTextField(
        label = { Text(text = "ChannelKey") },
        value = uiState.value.channelKey,
        onValueChange = { onChannelKeyChanged(it) },
        isError = uiState.value.hasChannelKeyError
    )
    Text(text = "Is push enabled?")
    Switch(
        checked = uiState.value.isPushEnabled,
        onCheckedChange = { onPushChanged(it) }
    )
}

@Composable
fun Content(
    user : User,
    fabAction: (User) -> Unit
) {


}
