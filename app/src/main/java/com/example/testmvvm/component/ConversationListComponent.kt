package com.example.testmvvm.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testmvvm.R
import com.example.testmvvm.model.Author
import com.example.testmvvm.model.AuthorType
import com.example.testmvvm.model.Conversation
import com.example.testmvvm.model.Message
import com.example.testmvvm.model.MessageContent
import com.example.testmvvm.viewModel.ConversationListUIState
import java.text.SimpleDateFormat
import java.util.Locale

private val LOG_TAG = "[ConversationItemComposable]"

@Composable
fun ConversationItemComposable(
    conversation : Conversation,
    localAuthor : Author,
    onConversationClick : (String) -> Unit
) {

    val lastMessage : Message = conversation.messages.last()

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            Log.d(LOG_TAG, conversation.toString())
            onConversationClick(conversation.id)
        }
        ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
        ) {
            LastAuthorComposable(name = lastMessage.author.name, isUnread = lastMessage.author.id != localAuthor.id)
            LastMessageComposable(content = lastMessage.content.value, isUnread = lastMessage.author.id != localAuthor.id)
            TimestampComposable(timestamp = conversation.messages.last().timestamp)
        }
    }
}

@Composable
private fun LastAuthorComposable(
    name : String,
    isUnread: Boolean
) {
    Text(
        fontWeight = if (isUnread) FontWeight.Bold else FontWeight.Normal,
        fontSize = 16.sp,
        modifier = Modifier,
        text = name
    )
}

@Composable
private fun LastMessageComposable(
    content : String,
    isUnread: Boolean
){
    Text(
        fontWeight = if (isUnread) FontWeight.Bold else FontWeight.Normal,
        fontSize = 12.sp,
        modifier = Modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        text = content
    )
}


@Composable
private fun TimestampComposable(
    timestamp: Long,
    modifier : Modifier = Modifier
) {
    Text(
        text = SimpleDateFormat(
            stringResource(R.string.timestamp_pattern),
            Locale.getDefault()
        )
            .format(timestamp),
        color = Color.DarkGray,
        fontSize = 8.sp,
        modifier = modifier
    )
}