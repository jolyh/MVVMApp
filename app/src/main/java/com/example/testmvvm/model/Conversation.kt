package com.example.testmvvm.model

import java.util.UUID

data class Conversation(
    val id: String = UUID.randomUUID().toString(),
    val participants: MutableList<Author> = ArrayList<Author>().toMutableList(),
    val messages: MutableList<Message> = ArrayList<Message>().toMutableList()
) {
    override fun toString(): String {
        return "[${this.javaClass.name}]" +
            "{ id: $id " +
            "- messages: $messages" +
            " }"
    }
}