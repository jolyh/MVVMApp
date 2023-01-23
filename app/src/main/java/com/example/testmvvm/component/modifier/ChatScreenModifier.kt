package com.example.testmvvm.component.modifier

data class ChatScreenModifier(
    val userMessageModifier: MessageModifier? = null,
    val businessMessageModifier: MessageModifier? = null,
    val composerModifier: ComposerModifier? = null
)