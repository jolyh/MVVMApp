package com.example.testmvvm.data

import com.example.testmvvm.model.User
import com.example.testmvvm.model.ZendeskSettings
import kotlinx.coroutines.flow.Flow

interface DataRepositoryInterface {

    // USER
    suspend fun saveUser(user: User)
    suspend fun getUser() : Flow<User>
    suspend fun clearUser()

    // Zendesk Settings
    suspend fun saveZendeskSettings(settings: ZendeskSettings)
    suspend fun getZendeskSettings() : Flow<ZendeskSettings>
    suspend fun clearZendeskSettings()

    suspend fun clearDataRepository()

}