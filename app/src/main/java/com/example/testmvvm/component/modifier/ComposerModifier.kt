package com.example.testmvvm.component.modifier

import android.R
import android.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

data class ComposerModifier(
    val modifier : Modifier = Modifier.padding(0.dp),
    val backgroundColor: Int = Color.WHITE,
    val outlineColor: Int = Color.BLACK,
    val inputFieldModifier: InputFieldModifier = InputFieldModifier(),
    val attachmentIconModifier: AttachmentIconModifier = AttachmentIconModifier(),
    val sendingIconModifier: SendingIconModifier = SendingIconModifier()
)

data class InputFieldModifier(
    val textColor: Int = Color.BLACK,
    val textFont: FontFamily = FontFamily.Default,
    val autoCorrection: Boolean = true,
    val capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    val modifier: Modifier = Modifier
)

data class AttachmentIconModifier(
    val iconColor: Int = Color.BLACK,
    val iconResource: Int = R.drawable.ic_menu_camera,
    val backgroundColor: Int = Color.WHITE,
    val buttonModifier: Modifier = Modifier,
    val iconModifier: Modifier = Modifier
)

data class SendingIconModifier(
    val iconColor: Int = Color.BLACK,
    val iconResource: Int = R.drawable.ic_menu_send,
)