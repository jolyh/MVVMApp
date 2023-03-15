package com.example.testmvvm.screen

import android.graphics.drawable.shapes.Shape
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import com.example.testmvvm.viewModel.SettingsViewModel
import com.example.testmvvm.viewModel.SettingsUIEvent
import com.example.testmvvm.viewModel.SettingsUIState

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val uiState = viewModel._uiState

    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            Fab {
                viewModel.onEvent(SettingsUIEvent.SubmitUser)
                viewModel.onEvent(
                    SettingsUIEvent.SubmitSettings
                ) {
                    Toast.makeText(context, "Settings Updated!", Toast.LENGTH_LONG).show()
                }
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
                onNameChanged = { viewModel.onEvent(SettingsUIEvent.UserNameChanged(it)) },
                onEmailChanged = { viewModel.onEvent(SettingsUIEvent.UserEmailChanged(it)) }
            )

            SettingContainer(
                uiState = uiState,
                onChannelKeyChanged = { viewModel.onEvent(SettingsUIEvent.ChannelKeyChanged(it)) },
                onPushChanged = { viewModel.onEvent(SettingsUIEvent.PushEnabledChanged(it)) }
            )

        }
    }
}

@Composable
fun Fab(fabOnClick : () -> Unit) {
    FloatingActionButton(onClick = { fabOnClick() }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "fab")
    }
}

@Composable
fun UserContainer(
    uiState: MutableState<SettingsUIState>,
    onNameChanged : (String) -> Unit,
    onEmailChanged : (String) -> Unit,
) {
    UserInput(uiState = uiState,
        onNameChanged = onNameChanged,
        onEmailChanged = onEmailChanged)
}

@Composable
fun SettingContainer(
    uiState: MutableState<SettingsUIState>,
    onChannelKeyChanged : (String) -> Unit,
    onPushChanged : (Boolean) -> Unit,
){
    SettingsInput(uiState = uiState,
        onChannelKeyChanged = onChannelKeyChanged,
        onPushChanged = onPushChanged)
}

@Composable
fun UserInput(
    uiState: MutableState<SettingsUIState>,
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
    uiState: MutableState<SettingsUIState>,
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
fun SnackbarFAB(text : String){
    Snackbar(
        shape = RectangleShape
    ) {
        Text(text = text)
    }
}
