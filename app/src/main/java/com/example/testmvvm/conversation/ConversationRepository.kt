package com.example.testmvvm.conversation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.testmvvm.data.DataRepository.Companion.CHANNEL_KEY_PREFERENCE
import com.example.testmvvm.data.DataRepository.Companion.EMAIL_PREFERENCE
import com.example.testmvvm.data.DataRepository.Companion.NAME_PREFERENCE
import com.example.testmvvm.data.DataRepository.Companion.PUSH_PREFERENCE
import com.example.testmvvm.data.DataRepositoryInterface
import com.example.testmvvm.data.datastore
import com.example.testmvvm.model.AuthorType
import com.example.testmvvm.model.Conversation
import com.example.testmvvm.model.Message
import com.example.testmvvm.model.MessageContent
import com.example.testmvvm.model.User
import com.example.testmvvm.model.ZendeskSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConversationRepository(private val context: Context) : ConversationRepositoryInterface {

    override suspend fun getConversation(id: String) : Conversation {

        // TODO IF id is not found, return empty conversation (conversation will have an id but no message)

        val messages = ArrayList<Message>()
        messages.add(Message(content = MessageContent(value = "message 1")))
        messages.add(Message(author = AuthorType.BUSINESS, content = MessageContent(value = "bus 1")))
        messages.add(Message(content = MessageContent(value = "cool 1")))
        messages.add(Message(content = MessageContent(value = "nice 1")))
        messages.add(Message(author = AuthorType.BUSINESS, content = MessageContent(value = "bus2 1")))
        messages.add(Message(author = AuthorType.BUSINESS, content = MessageContent(value = "bus 3")))
        messages.add(Message(content = MessageContent(value = "user")))
        messages.add(Message(content = MessageContent(value = "user 1")))
        messages.add(Message(content = MessageContent(value = "user user 1")))
        messages.add(Message(content = MessageContent(value = "long user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user message")))
        messages.add(Message(author = AuthorType.BUSINESS, content = MessageContent(value = "long business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business message")))

        return Conversation(id, messages.toMutableList())
    }

    override suspend fun saveConversation(conversation: Conversation) {
        TODO("Not yet implemented")
    }
}