package com.example.testmvvm.component

import android.R
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.testmvvm.component.modifier.ComposerModifier
import com.example.testmvvm.model.Author
import com.example.testmvvm.model.Conversation
import com.example.testmvvm.ui.theme.TestMVVMTheme
import com.example.testmvvm.viewModel.ChatUIState

@Composable
fun ComposerComposable(
    modifier: Modifier = Modifier,
    uiState: MutableState<ChatUIState>,
    withAttachment : Boolean = false,
    onSendIconClick : () -> Unit
){
    if (uiState.value.composerVisible) {
        Row(
            modifier = modifier
                .fillMaxSize()
        ) {

            if (withAttachment)
                ComposerAttachmentButton(
                    iconResource = R.drawable.ic_menu_camera,
                    onAttachmentClick = {}
                )
            ComposerInput(
                modifier = Modifier.fillMaxSize(0.9f),
                uiState = uiState
            )
            ComposerSendButton { onSendIconClick() }
        }
    }
}

@Composable
fun ComposerAttachmentButton(
    modifier: Modifier = Modifier,
    iconResource : Int = R.drawable.ic_menu_camera,
    onAttachmentClick: () -> Unit,
){
    IconButton(
        onClick = { onAttachmentClick() },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = "Send Message Icon"
        )
    }
}

@Composable
fun ComposerInput(
    modifier : Modifier = Modifier,
    uiState: MutableState<ChatUIState>,
){
    TextField(
        value = uiState.value.composerValue,
        onValueChange = {
            uiState.value = uiState.value.copy(composerValue = it)
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters,
            autoCorrect = true,
            keyboardType = KeyboardType.Text
        ),
        modifier = modifier
    )
}

@Composable
fun ComposerSendButton(
    modifier: Modifier = Modifier,
    sendIconResource : Int = R.drawable.ic_menu_send,
    onSendIconClick: () -> Unit
){
    IconButton(
        onClick = { onSendIconClick() },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(sendIconResource),
            contentDescription = "Send Message Icon"
        )
    }
}

@Composable
@Preview(
    name = "Composer",
    showBackground = true
)
fun ComposerPreview(){

    val ui = ChatUIState(Conversation(), Author(), "", true)
    val uiState : MutableState<ChatUIState> = remember { mutableStateOf(ui) }

    TestMVVMTheme() {
        ComposerComposable(uiState = uiState) {
            Log.d("PREVIEW", uiState.value.composerValue)
            uiState.value = uiState.value.copy(composerValue = "")
        }
    }
}