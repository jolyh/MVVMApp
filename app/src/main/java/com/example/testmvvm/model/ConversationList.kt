package com.example.testmvvm.model

import java.util.UUID

data class ConversationList(
    val id: String = UUID.randomUUID().toString(),
    val user : Author = Author(),
    val conversations: MutableList<Conversation> = ArrayList<Conversation>().toMutableList()
) {
    override fun toString(): String {
        return "[${this.javaClass.name}]" +
            "{ id: $id " +
            "- conversations: $conversations" +
            " }"
    }
}