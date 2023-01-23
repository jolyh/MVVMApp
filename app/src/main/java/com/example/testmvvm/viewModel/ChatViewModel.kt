package com.example.testmvvm.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmvvm.conversation.ConversationRepository
import com.example.testmvvm.model.Conversation
import com.example.testmvvm.model.Message
import com.example.testmvvm.model.MessageContent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

sealed class ChatUIEvent {

    // CONVERSATION
    class FetchOrCreateConversation(val id : String): ChatUIEvent()
    object RefreshConversation: ChatUIEvent()
    object SubmitMessage : ChatUIEvent()

    // COMPOSER
    object ShowComposer : ChatUIEvent()
    object DismissComposer : ChatUIEvent()

    object ClearUI : ChatUIEvent()

}

data class ChatUIState(

    val conversation: Conversation = Conversation("0"),
    val composerValue : String = "",
    val composerVisible: Boolean = true,

) {
    override fun toString(): String {
        return (
            "[ChatUIState]:"
                + " conversation: " + conversation
            )
    }
}

/* TODO NEED to implement the conversation ID on navigation so we open the appropriate one
    Default implementation should have empty conversation
 */

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ConversationRepository,
    //private val conversationId: String = "1"
    ) : ViewModel() {

    private val LOG_TAG = "[${this.javaClass.name}]"

    val _uiState : MutableState<ChatUIState> = mutableStateOf(ChatUIState())

    init {
        Log.d(LOG_TAG, "init")
        fetchOrCreateConversation("0")
    }

    // UI EVENT
    fun onEvent(event: ChatUIEvent) {
        when(event) {
            is ChatUIEvent.RefreshConversation -> {
                refreshConversation()
            }
            is ChatUIEvent.SubmitMessage -> {
                submitMessage()
            }
            is ChatUIEvent.FetchOrCreateConversation -> {
                fetchOrCreateConversation(event.id)
            }
            is ChatUIEvent.ShowComposer -> {
                _uiState.value = _uiState.value.copy(composerVisible = true)
            }
            is ChatUIEvent.DismissComposer -> {
                _uiState.value = _uiState.value.copy(composerVisible = false)
            }
            is ChatUIEvent.ClearUI -> {
                clearUI()
            }
            else -> {
                Log.d(LOG_TAG, "Unsupported Event")
            }
        }
    }

    private fun fetchOrCreateConversation(id : String) {
        viewModelScope.launch {
            val repoConversation = repository.getConversation(id)
            _uiState.value = _uiState.value.copy(
                    conversation = repoConversation
            )
        }
    }

    private fun refreshConversation() {
        viewModelScope.launch {
            val repoConversation = repository.getConversation(_uiState.value.conversation.id)
            _uiState.value = _uiState.value.copy(
                conversation = repoConversation
            )
        }
    }

    private fun saveConversation(){
        viewModelScope.launch {
            repository.saveConversation(_uiState.value.conversation)
        }
    }

    private fun submitMessage(
        text : String = _uiState.value.composerValue)
    {
        if (text.isBlank()){
            return
        }
        _uiState.value.conversation.messages
            .add(Message(content = MessageContent(value = text)))
    }

    // CLEARING
    private fun clearUI(){
        saveConversation()
        _uiState.value = ChatUIState(Conversation())
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        // TODO save datastore if needed
        saveConversation()
        super.onCleared()
    }
}