package com.example.testmvvm.component.modifier

import androidx.compose.material.Colors
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily

data class MessageModifier(
    val authorModifier : AuthorModifier,
    val messageContentModifier: MessageContentModifier,
    val timestampModifier: TimestampModifier,
    val iconModifier: SentIconModifier,
)

data class AuthorModifier(
    val textColor : Colors,
    val textFont : FontFamily,
    val iconColor : Colors,
    val iconResource : Int,
)

data class MessageContentModifier(
    val textColor: Colors,
    val textFontFamily: FontFamily,
    val backgroundColor : Colors,
    val outlineColor: Colors
)

data class TimestampModifier(
    val timestampFormat : String,
    val textColor: Colors,
    val textFontFamily: FontFamily,
)

data class SentIconModifier(
    val iconColor : Colors,
    val iconResource : Int,
)