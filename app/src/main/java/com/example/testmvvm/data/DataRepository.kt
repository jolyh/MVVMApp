package com.example.testmvvm.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.testmvvm.helpers.DEFAULT_AUTHOR_EMAIL
import com.example.testmvvm.helpers.DEFAULT_AUTHOR_ID
import com.example.testmvvm.helpers.DEFAULT_AUTHOR_NAME
import com.example.testmvvm.helpers.ifNullOrEmpty
import com.example.testmvvm.model.Author
import com.example.testmvvm.model.AuthorType
import com.example.testmvvm.model.ZendeskSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val DATASTORE_NAME = "DATASTORE"

val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class DataRepository(private val context: Context) : DataRepositoryInterface {

    companion object {

        private const val USERID_KEY = "USERID"
        private const val NAME_KEY = "NAME"
        private const val EMAIL_KEY = "EMAIL"
        private const val CHANNEL_KEY = "CHANNEL_KEY"
        private const val PUSH = "PUSH"

        val USERID_PREFERENCE = stringPreferencesKey(USERID_KEY)
        val NAME_PREFERENCE = stringPreferencesKey(NAME_KEY)
        val EMAIL_PREFERENCE = stringPreferencesKey(EMAIL_KEY)

        val CHANNEL_KEY_PREFERENCE = stringPreferencesKey(CHANNEL_KEY)
        val PUSH_PREFERENCE = booleanPreferencesKey(PUSH)

    }

    // USER

    override suspend fun getLocalAuthor(): Flow<Author> =
        context.datastore.data.map {
            Author(
                id = it[USERID_PREFERENCE].ifNullOrEmpty(DEFAULT_AUTHOR_ID),
                name = it[NAME_PREFERENCE].ifNullOrEmpty(DEFAULT_AUTHOR_NAME),
                authorType = AuthorType.USER,
                email = it[EMAIL_PREFERENCE].ifNullOrEmpty(DEFAULT_AUTHOR_EMAIL)
            )
        }

    override suspend fun saveLocalAuthor(author: Author) {
        context.datastore.edit {
            it[USERID_PREFERENCE] = author.id
            it[NAME_PREFERENCE] = author.name
            it[EMAIL_PREFERENCE] = author.email
        }
    }

    override suspend fun clearLocalUser() {
        context.datastore.edit {
            it.remove(USERID_PREFERENCE)
            it.remove(NAME_PREFERENCE)
            it.remove(EMAIL_PREFERENCE)
        }
    }

    // ZENDESK SETTINGS

    override suspend fun getZendeskSettings(): Flow<ZendeskSettings> =
        context.datastore.data.map {
            ZendeskSettings(
                channelKey = it[CHANNEL_KEY_PREFERENCE].orEmpty(),
                isPushEnabled = it[PUSH_PREFERENCE] == true
            )
        }

    override suspend fun saveZendeskSettings(settings: ZendeskSettings) {
        context.datastore.edit {
            it[CHANNEL_KEY_PREFERENCE] = settings.channelKey
            it[PUSH_PREFERENCE] = settings.isPushEnabled
        }
    }

    override suspend fun clearZendeskSettings() {
        context.datastore.edit {
            it.remove(CHANNEL_KEY_PREFERENCE)
            it.remove(PUSH_PREFERENCE)
        }
    }

    // CLEAR ALL

    override suspend fun clearDataRepository() {
        context.datastore.edit {
            it.clear()
        }
    }
}