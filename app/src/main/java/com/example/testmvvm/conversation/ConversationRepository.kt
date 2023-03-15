package com.example.testmvvm.conversation

import android.content.Context
import androidx.compose.runtime.MutableState
import com.example.testmvvm.model.Author
import com.example.testmvvm.model.AuthorType
import com.example.testmvvm.model.Conversation
import com.example.testmvvm.model.Message
import com.example.testmvvm.model.MessageContent
import kotlinx.coroutines.flow.Flow

class ConversationRepository(private val context: Context) : ConversationRepositoryInterface {

    val CurrentConversationList : MutableList<Conversation> = ArrayList<Conversation>().toMutableList()

    override suspend fun getConversationList(userId: String): MutableList<Conversation> {
        //TODO("Not yet implemented")
        return getDummyList()
    }

    override suspend fun getConversation(id: String): Conversation {

        // TODO("Check user from datastore")

        val participants = ArrayList<Author>()
        participants.add(Author())

        // TODO IF id is not found, return empty conversation (conversation will have an id but no message)
        val messages = getDummyConversation(id)

        return Conversation(id, participants, messages.toMutableList())
    }

    override suspend fun saveConversation(conversation: Conversation) {
        TODO("Not yet implemented")

    }

    private fun getDummyList() : ArrayList<Conversation> {

        val businessAuthor = Author(name = "Zendesk BOT", authorType = AuthorType.BUSINESS)
        val localAuthor = Author()

        val messages = ArrayList<Message>()
        messages.add(Message(author = localAuthor, content = MessageContent(value = "message 1")))
        messages.add(Message(author = businessAuthor, content = MessageContent(value = "bus 1")))
        messages.add(Message(author = localAuthor,content = MessageContent(value = "cool 1")))
        messages.add(Message(author = localAuthor,content = MessageContent(value = "nice 1")))
        messages.add(Message(author = businessAuthor, content = MessageContent(value = "bus2 1")))
        messages.add(Message(author = businessAuthor, content = MessageContent(value = "bus 3")))
        messages.add(Message(author = localAuthor,content = MessageContent(value = "user")))
        messages.add(Message(author = localAuthor,content = MessageContent(value = "user 1")))
        messages.add(Message(author = localAuthor,content = MessageContent(value = "user user 1")))
        messages.add(Message(author = localAuthor,content = MessageContent(value = "long user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user message")))
        messages.add(Message(author = businessAuthor, content = MessageContent(value = "long business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business message")))

        val messages1 = ArrayList<Message>()
        messages1.add(Message(author = localAuthor,content = MessageContent(value = "message 1")))
        messages1.add(Message(author = businessAuthor, content = MessageContent(value = "bus 1")))
        messages1.add(Message(author = localAuthor,content = MessageContent(value = "cool 1")))
        messages1.add(Message(author = localAuthor,content = MessageContent(value = "nice 1")))


        val messages2 = ArrayList<Message>()
        messages2.add(Message(author = localAuthor,content = MessageContent(value = "message 1")))

        val conversations = ArrayList<Conversation>()
        conversations.add(Conversation(id = "2", messages = messages2))
        conversations.add(Conversation(id = "0", messages = messages))
        conversations.add(Conversation(id = "1", messages = messages1))

        return conversations
    }

    private fun getDummyConversation(id : String) : ArrayList<Message> {

        val businessAuthor = Author(name = "Zendesk BOT", authorType = AuthorType.BUSINESS)
        val localAuthor = Author()

        val messages = ArrayList<Message>()
        if (id == "0") {
            messages.add(Message(author = localAuthor,content = MessageContent(value = "message 1")))
            messages.add(Message(author = businessAuthor, content = MessageContent(value = "bus 1")))
            messages.add(Message(author = localAuthor,content = MessageContent(value = "cool 1")))
            messages.add(Message(author = localAuthor,content = MessageContent(value = "nice 1")))
            messages.add(Message(author = businessAuthor, content = MessageContent(value = "bus2 1")))
            messages.add(Message(author = businessAuthor, content = MessageContent(value = "bus 3")))
            messages.add(Message(author = localAuthor,content = MessageContent(value = "user")))
            messages.add(Message(author = localAuthor,content = MessageContent(value = "user 1")))
            messages.add(Message(author = localAuthor,content = MessageContent(value = "user user 1")))
            messages.add(Message(author = localAuthor,content = MessageContent(value = "long user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user messagelong user message")))
            messages.add(Message(author = businessAuthor,
                content = MessageContent(value = "long business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business messagelong business message")))
        } else if (id == "1"){
            messages.add(Message(author = localAuthor,content = MessageContent(value = "message 1")))
            messages.add(Message(author = businessAuthor, content = MessageContent(value = "bus 1")))
            messages.add(Message(author = localAuthor,content = MessageContent(value = "cool 1")))
            messages.add(Message(author = localAuthor,content = MessageContent(value = "nice 1")))
        } else if (id == "2") {
            messages.add(Message(author = localAuthor,content = MessageContent(value = "message 1")))
        } else if (id == "3"){
        } else {
            messages.add(Message(author = localAuthor,content = MessageContent(value = "New conversation")))
        }
        return messages
    }
}