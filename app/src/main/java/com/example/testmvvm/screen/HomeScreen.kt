package com.example.testmvvm.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.testmvvm.model.Author

private val LOG_TAG = "[HomeScreenContent]"

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    onChatClick: (String) -> Unit,
    onConversationListClick: (String) -> Unit
) {
    HomeScreenContent(
        onSettingsClick = onSettingsClick,
        onChatClick =  onChatClick,
        onConversationListClick = onConversationListClick
    )
}

@Composable
fun HomeScreenContent(
    onSettingsClick: () -> Unit,
    onChatClick: (String) -> Unit,
    onConversationListClick: (String) -> Unit
){

    val input = remember { mutableStateOf("0") }

    val author = Author()

    Log.d(LOG_TAG, author.toString())

    Scaffold { it ->
        Column(modifier = Modifier
            .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "HOME"
            )
            Button(onClick = { onSettingsClick() }
            ) {
                Text(text = "settings")
            }
            Button(onClick = { onChatClick(input.value) }
            ) {
                Text(text = "chat")
            }
            TextField(
                value = input.value,
                onValueChange = { value ->
                    input.value = value
                }
            )
            Button(onClick = {
                onConversationListClick(author.id)
            }
            ) {
                Text(text = "conversation list")
            }
        }
    }
}