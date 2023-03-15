package com.example.testmvvm.component

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testmvvm.model.AuthorType
import com.example.testmvvm.model.Message
import com.example.testmvvm.model.MessageType
import com.example.testmvvm.model.SendingStatus
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MessageComposable(
    modifier: Modifier = Modifier,
    message : Message
){
    Row(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement =
        if (message.author.authorType === AuthorType.USER) Arrangement.End
        else Arrangement.Start
    ) {
        // SPACER if author is user
        if (message.author.authorType === AuthorType.USER)
            Spacer(modifier = Modifier.fillMaxWidth(0.3f))
        // AUTHOR ICON
        AuthorIconComposable(authorType = message.author.authorType)
        // AUTHOR NAME + MESSAGE CONTENT
        AuthorNameAndMessageContentComposable(message)
        // SPACER if author is Business
        if (message.author.authorType != AuthorType.USER)
            Spacer(modifier = Modifier.fillMaxWidth(0.3f))
    }
}

@Composable
private fun AuthorIconComposable(
    authorType: AuthorType,
    imageResource : Int = R.drawable.stat_sys_headset,
){
    Column(
        modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp)
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Image",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    color =
                    if (authorType == AuthorType.USER) MaterialTheme.colors.primary
                    else  MaterialTheme.colors.secondary
                )
                .border(1.5.dp, MaterialTheme.colors.primary, CircleShape)
        )
    }
}

@Composable
private fun AuthorNameAndMessageContentComposable(
    message : Message
){
    val isExpanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .clickable { isExpanded.value = !isExpanded.value }
            .clip(RoundedCornerShape(1.dp))
    ) {
        // TODO Get author name
        Text(
            text = message.author.name,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.subtitle2
        )
        // Add a vertical space between the author and message texts
        Spacer(modifier = Modifier.height(2.dp))
        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 1.dp
        ) {
            MessageContentComposable(message = message, isExpanded = isExpanded)
        }
        Row(
            modifier = Modifier.align(Alignment.End)
        ) {
            if (isExpanded.value)
                MessageTimestampComposable(timestamp = message.timestamp)
            if (message.status === SendingStatus.SENT) {
                SentIcon()
            }
        }
    }
}

@Composable
private fun AuthorName(
    name : String,
    authorType: AuthorType
) {
    Text(
        text = name,
        color =
        if (authorType == AuthorType.USER)
            MaterialTheme.colors.primary
        else
            MaterialTheme.colors.secondary
        ,
        style = MaterialTheme.typography.subtitle2
    )
}

@Composable
private fun MessageContentComposable(
    message: Message,
    isExpanded: MutableState<Boolean>,
    modifier : Modifier = Modifier
) {
    // TODO ADD all display for message content
    when (message.content.type) {
        MessageType.TEXT -> {
            MessageTextComposable(message = message, isExpanded = isExpanded, modifier)
        }
        else -> {
        }
    }
}
@Composable
private fun MessageTextComposable(
    message: Message,
    isExpanded: MutableState<Boolean>,
    modifier : Modifier = Modifier
) {
    val content = message.content
    Text(
        text = content.value,
        modifier = modifier.padding(all = 4.dp),
        maxLines = if (isExpanded.value) Int.MAX_VALUE else 2,
        style = MaterialTheme.typography.body2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun SentIcon(
    modifier : Modifier = Modifier
){
    Image(
        painter = painterResource(id = R.drawable.ic_media_play),
        modifier = modifier
            .rotate(90f)
            .size(16.dp),
        contentDescription = "Sent Icon"
    )
}
@Composable
private fun MessageTimestampComposable(
    timestamp: Long,
    modifier : Modifier = Modifier
) {
    Text(
        text = SimpleDateFormat(
            stringResource(com.example.testmvvm.R.string.timestamp_pattern),
            Locale.getDefault()
        )
            .format(timestamp),
        color = Color.DarkGray,
        fontSize = 8.sp,
        modifier = modifier
    )
}