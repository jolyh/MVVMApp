package com.example.testmvvm.conversation

import com.example.testmvvm.model.Conversation
import com.example.testmvvm.model.User
import com.example.testmvvm.model.ZendeskSettings
import kotlinx.coroutines.flow.Flow

interface ConversationRepositoryInterface {

    suspend fun getConversation(id: String) : Conversation?

    suspend fun saveConversation(conversation: Conversation)

}