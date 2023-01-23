package com.example.testmvvm.model

data class Conversation(
    val id: String = java.util.UUID.randomUUID().toString(),
    val messages : MutableList<Message> = ArrayList<Message>().toMutableList()
) {
    override fun toString(): String {
        return "[${this.javaClass.name}]" +
            "{ id: $id " +
            "- messages: $messages" +
            " }"
    }
}