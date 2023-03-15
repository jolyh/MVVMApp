package com.example.testmvvm.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.overscroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testmvvm.component.ComposerComposable
import com.example.testmvvm.component.ConversationItemComposable
import com.example.testmvvm.component.MessageComposable
import com.example.testmvvm.model.AuthorType
import com.example.testmvvm.model.Conversation
import com.example.testmvvm.model.Message
import com.example.testmvvm.model.MessageContent
import com.example.testmvvm.ui.theme.TestMVVMTheme
import com.example.testmvvm.viewModel.ChatUIEvent
import com.example.testmvvm.viewModel.ChatUIState
import com.example.testmvvm.viewModel.ChatViewModel
import com.example.testmvvm.viewModel.ConversationListUIState
import com.example.testmvvm.viewModel.ConversationListViewModel

private val LOG_TAG = "[ChatScreenContent]"

@Composable
fun ConversationListScreen(
    viewModel: ConversationListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onConversationClick : (String) -> Unit
) {

    ConversationListContent(
        conversationListUIState = viewModel._uiState,
        onConversationClick = onConversationClick
    )

}

@Composable
fun ConversationListContent (
    modifier: Modifier = Modifier,
    conversationListUIState: MutableState<ConversationListUIState>,
    onConversationClick: (String) -> Unit
){

    Scaffold() {
        ConversationListComposable(
            modifier = Modifier.padding(it),
            conversationList = conversationListUIState.value.conversationList,
            uiState = conversationListUIState,
            onConversationClick = onConversationClick
        )
    }
}

@Composable
fun ConversationListComposable(
    modifier: Modifier = Modifier,
    conversationList : MutableList<Conversation>,
    uiState: MutableState<ConversationListUIState>,
    onConversationClick: (String) -> Unit
){

    val listState = rememberLazyListState()
    val listSize = conversationList.size

    LaunchedEffect(listSize) {
        listState.animateScrollToItem(listSize)
    }
    LazyColumn(
        modifier = modifier,
        state = listState,
    ){
        items(conversationList) { conversation ->
            ConversationItemComposable(
                conversation = conversation,
                localAuthor = uiState.value.localAuthor,
                onConversationClick = onConversationClick
            )
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}