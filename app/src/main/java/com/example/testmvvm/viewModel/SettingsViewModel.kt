package com.example.testmvvm.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmvvm.data.DataRepository
import com.example.testmvvm.model.User
import com.example.testmvvm.model.ZendeskSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

sealed class UIEvent {
    // USER
    data class UserNameChanged(val name : String) : UIEvent()
    data class UserEmailChanged(val email : String) : UIEvent()
    object SubmitUser: UIEvent()

    // CHANNEL KEY
    data class ChannelKeyChanged(val key : String) : UIEvent()
    data class PushEnabledChanged(val isPushEnabled : Boolean) : UIEvent()
    object SubmitSettings: UIEvent()

    // CLEARING
    data class ClearUIValue(val key: String) : UIEvent()
    object ClearUI : UIEvent()
    object ClearStorage : UIEvent()
}

data class UIState(

    // USER
    val userName : String = "",
    val userEmail : String = "",
    val hasNameError : Boolean = false,
    val hasEmailError : Boolean = false,

    // ZD SETTINGS
    val channelKey : String = "",
    val isPushEnabled: Boolean = false,
    val hasChannelKeyError : Boolean = false

) {
    override fun toString(): String {
        return (
            "[UIState]:"
                + " userName: " + userName
                + " - userEmail: " + userEmail
                + " - channelKey: " + channelKey
                + " - isPushEnabled: " + isPushEnabled.toString()
            )
    }
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: DataRepository
    ) : ViewModel() {

    private val LOG_TAG = "[${this.javaClass.name}]"

    val _uiState : MutableState<UIState> = mutableStateOf(UIState())

    init {
        Log.d(LOG_TAG, "init")
        getUserData()
        getSettingsData()
    }

    // UI EVENT
    fun onEvent(event: UIEvent) {
        when(event) {
            is UIEvent.UserNameChanged -> {
                _uiState.value = _uiState.value.copy(
                    userName = event.name
                )
            }
            is UIEvent.UserEmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    userEmail = event.email
                )
            }
            is UIEvent.SubmitUser -> {
                validateUserInputs()
            }
            is UIEvent.ChannelKeyChanged -> {
                _uiState.value = _uiState.value.copy(
                    channelKey = event.key
                )
            }
            is UIEvent.PushEnabledChanged -> {
                _uiState.value = _uiState.value.copy(
                    isPushEnabled = event.isPushEnabled
                )
            }
            is UIEvent.SubmitSettings -> {
                validateSettingsInputs()
            }
            is UIEvent.ClearUIValue -> {
                clearUIValue(event.key)
            }
            is UIEvent.ClearUI -> {
                clearUI()
            }
            is UIEvent.ClearStorage -> {
                clearStorage()
            }
            else -> {
                Log.d(LOG_TAG, "Unsupported Event")
            }
        }
    }

    // DATASTORE USER
    private fun saveUserData() {
        Log.d(LOG_TAG, "saveUserData ${_uiState.value}")
        viewModelScope.launch {
            repository.saveUser(
                User(_uiState.value.userName, _uiState.value.userEmail)
            )
        }
    }
    private fun getUserData() {
        Log.d(LOG_TAG, "get data ")
        viewModelScope.launch {
            val repoUser = repository.getUser().firstOrNull()
            Log.d(LOG_TAG, "get data -> " + repoUser.toString())
            if (repoUser != null) {
                _uiState.value = _uiState.value.copy(
                    userName = repoUser.name,
                    userEmail = repoUser.email
                )
            }
        }
    }

    // DATASTORE SETTINGS
    private fun saveSettingsData() {
        Log.d(LOG_TAG, "saveSettingsData ${_uiState.value}")
        viewModelScope.launch {
            repository.saveZendeskSettings(
                ZendeskSettings(_uiState.value.channelKey, _uiState.value.isPushEnabled)
            )
        }
    }
    private fun getSettingsData() {
        viewModelScope.launch {
            val repoSettings = repository.getZendeskSettings().firstOrNull()
            Log.d(LOG_TAG, "getSettingsData -> " + repoSettings.toString())
            if (repoSettings != null) {
                _uiState.value = _uiState.value.copy(
                    channelKey = repoSettings.channelKey,
                    isPushEnabled = repoSettings.isPushEnabled
                )
            }
        }
    }

    // CLEARING
    private fun clearUIValue(key: String){
        when(key) {
            "name" -> {
                _uiState.value.copy(userName = "")
            }
            "email" -> {
                _uiState.value.copy(userEmail = "")
            }
            "channelKey" -> {
                _uiState.value.copy(channelKey = "")
            }
        }
    }
    private fun clearUI(){
        _uiState.value = UIState()
    }
    private fun clearStorage(){
        _uiState.value = UIState()
        viewModelScope.launch {
            repository.clearDataRepository()
        }
    }

    // INPUT VALIDATION
    private fun validateUserInputs(){
        val isNameValid = _uiState.value.userName.isNotBlank()
        val isEmailValid = _uiState.value.userEmail.isNotBlank()

        _uiState.value = _uiState.value.copy(
            hasNameError = !isNameValid,
            hasEmailError = !isEmailValid
        )

        // Check if any invalid input
        if (!isNameValid || !isEmailValid) {
            Log.d(LOG_TAG, "Invalid name or email, please verify input.")
        } else {
            saveUserData()
        }
    }

    private fun validateSettingsInputs(){
        val isKeyValid = _uiState.value.channelKey.isNotBlank()
        _uiState.value = _uiState.value.copy(
            hasChannelKeyError = !isKeyValid
        )

        // Check if any invalid input
        if (!isKeyValid) {
            Log.d(LOG_TAG, "Invalid channelKey, please verify input.")
        } else {
            saveSettingsData()
        }
    }



    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        validateUserInputs()
    }


}