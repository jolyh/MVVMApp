package com.example.testmvvm.data

import com.example.testmvvm.model.Author
import com.example.testmvvm.model.ZendeskSettings
import kotlinx.coroutines.flow.Flow

interface DataRepositoryInterface {

    // USER
    suspend fun saveLocalAuthor(author: Author)
    suspend fun getLocalAuthor() : Flow<Author>
    suspend fun clearLocalUser()

    // Zendesk Settings
    suspend fun saveZendeskSettings(settings: ZendeskSettings)
    suspend fun getZendeskSettings() : Flow<ZendeskSettings>
    suspend fun clearZendeskSettings()

    suspend fun clearDataRepository()

}