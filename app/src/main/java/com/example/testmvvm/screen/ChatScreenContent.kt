package com.example.testmvvm.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Back
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testmvvm.component.ComposerComposable
import com.example.testmvvm.component.MessageComposable
import com.example.testmvvm.component.modifier.ChatScreenModifier
import com.example.testmvvm.model.Conversation
import com.example.testmvvm.ui.theme.TestMVVMTheme
import com.example.testmvvm.viewModel.ChatUIEvent
import com.example.testmvvm.viewModel.ChatUIState
import com.example.testmvvm.viewModel.ChatViewModel

private val LOG_TAG = "[ChatScreenContent]"

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {

    val uiState = viewModel._uiState

    Scaffold {
        Column(Modifier.padding(it)) {
            Row(Modifier.fillMaxSize(0.92f)) {
                ConversationComposable(
                    modifier = Modifier.padding(4.dp),
                    uiState = uiState,

                )
            }
            ComposerComposable(
                modifier = Modifier
                    .background(color = MaterialTheme.colors.secondaryVariant),
                uiState = uiState,
                withAttachment = true,
            ) {
                    Log.d("LOG_TAG", uiState.value.composerValue)
                    viewModel.onEvent(ChatUIEvent.SubmitMessage)
                    uiState.value = uiState.value.copy(composerValue = "")
                }
            }
    }

    BackHandler() {

    }
}

@Composable
fun ConversationComposable(
    modifier: Modifier = Modifier,
    uiState: MutableState<ChatUIState>,
){
    val listState = rememberLazyListState()
    val listSize = uiState.value.conversation.messages.size

    Log.d("TAG", uiState.value.conversation.toString())

    LaunchedEffect(listSize) {
        listState.animateScrollToItem(listSize)
    }
    LazyColumn(
        modifier = modifier,
        state = listState
    ){
        items(uiState.value.conversation.messages) { message ->
            MessageComposable(message = message)
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}

@Composable
@Preview(
    name = "ChatScreen",
    showBackground = true
)
fun ConversationPreview(){

    val ui = ChatUIState(Conversation(), "", true)
    val uiState : MutableState<ChatUIState> = remember { mutableStateOf(ui) }

    TestMVVMTheme {
        ConversationComposable(uiState = uiState)
    }
}