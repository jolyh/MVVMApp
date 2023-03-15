package com.example.testmvvm.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmvvm.conversation.ConversationRepository
import com.example.testmvvm.data.DataRepository
import com.example.testmvvm.model.Author
import com.example.testmvvm.model.Conversation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

sealed class ConversationListUIEvent {

    // CONVERSATION
    object FetchConversationList: ConversationListUIEvent()
    //class OpenConversation(val conversationId : String): ConversationListUIEvent()

}

data class ConversationListUIState(

    val localAuthor: Author = Author(),
    val conversationList: MutableList<Conversation> = ArrayList<Conversation>()

) {
    override fun toString(): String {
        return (
            "[ConversationListUIState]:"
                + " conversations: " + conversationList
            )
    }
}

@HiltViewModel
class ConversationListViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val dataRepository: DataRepository,
    private val savedState : SavedStateHandle
    ) : ViewModel() {

    private val LOG_TAG = "[${this.javaClass.name}]"

    val _uiState : MutableState<ConversationListUIState> =
        mutableStateOf(ConversationListUIState())

    init {
        Log.d(LOG_TAG, "init")

        fetchLocalUser()

        fetchConversationList()
    }

    // UI EVENT
    fun onEvent(event: ConversationListUIEvent) {
        when(event) {
            is ConversationListUIEvent.FetchConversationList -> {
                fetchConversationList()
            }
            //is ConversationListUIEvent.OpenConversation -> { openConversation(event.conversationId) }
            else -> {
                Log.d(LOG_TAG, "Unsupported Event")
            }
        }
    }

    private fun fetchLocalUser() {
        viewModelScope.launch {
            dataRepository.getLocalAuthor().collect{
                _uiState.value = _uiState.value.copy(localAuthor = it)
            }
        }
    }

    private fun fetchConversationList() {
        viewModelScope.launch {
            val repoConversationList = conversationRepository
                .getConversationList(_uiState.value.localAuthor.id)
            _uiState.value = _uiState.value.copy(
                    conversationList = repoConversationList
            )
        }
    }

    private fun openConversation(conversationId : String) {
        // Adding the method to move to conv here or in screen?
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        // TODO save datastore if needed
        super.onCleared()
    }
}