package com.example.testmvvm.conversation

import androidx.compose.runtime.MutableState
import com.example.testmvvm.model.Conversation

interface ConversationRepositoryInterface {


    suspend fun getConversationList(userId : String) : MutableList<Conversation>

    suspend fun getConversation(id: String) : Conversation

    suspend fun saveConversation(conversation: Conversation)

}