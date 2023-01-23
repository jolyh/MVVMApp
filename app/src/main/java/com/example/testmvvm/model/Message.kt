package com.example.testmvvm.model

data class Message(
    val id : String = java.util.UUID.randomUUID().toString(),
    val author : AuthorType = AuthorType.USER,
    val content : MessageContent,
    val timestamp: Long = System.currentTimeMillis()/1000,
    val status : SendingStatus = SendingStatus.SENDING
) {
    override fun toString(): String {
        return "[${this.javaClass.name}] " +
            "{ " +
            "id: $id" +
            " - author: $author" +
            " - content: ${content.toString()}" +
            " - timestamp: $timestamp" +
            " - status: $status" +
            " }"
    }
}

data class MessageContent(
    val type : MessageType = MessageType.TEXT,
    val value : String = ""
) {
    override fun toString(): String {
        return "[${this.javaClass.name}] " +
            "{ " +
            "type: $type" +
            " - value: $value" +
            " }"
    }
}

enum class SendingStatus {
    SENDING, SENT, FAILED
}

enum class MessageType {
    TEXT, FORM
}

enum class AuthorType {
    USER, BUSINESS
}