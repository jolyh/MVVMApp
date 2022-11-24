package com.example.testmvvm.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.testmvvm.model.User
import com.example.testmvvm.model.ZendeskSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val DATASTORE_NAME = "DATASTORE"

val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class DataRepository(private val context: Context) : DataRepositoryInterface {

    companion object {

        private const val NAME_KEY = "NAME"
        private const val EMAIL_KEY = "EMAIL"
        private const val CHANNEL_KEY = "CHANNEL_KEY"
        private const val PUSH = "PUSH"

        val NAME_PREFERENCE = stringPreferencesKey(NAME_KEY)
        val EMAIL_PREFERENCE = stringPreferencesKey(EMAIL_KEY)

        val CHANNEL_KEY_PREFERENCE = stringPreferencesKey(CHANNEL_KEY)
        val PUSH_PREFERENCE = booleanPreferencesKey(PUSH)

    }

    override suspend fun saveUser(user: User) {
        context.datastore.edit {
            it[NAME_PREFERENCE] = user.name
            it[EMAIL_PREFERENCE] = user.email
        }
    }
    override suspend fun getUser(): Flow<User> =
        context.datastore.data.map {
            User(
                name = it[NAME_PREFERENCE].orEmpty(),
                email = it[EMAIL_PREFERENCE].orEmpty()
            )
        }

    override suspend fun clearUser() {
        context.datastore.edit {
            it.remove(NAME_PREFERENCE)
            it.remove(EMAIL_PREFERENCE)
        }
    }

    override suspend fun saveZendeskSettings(settings: ZendeskSettings) {
        context.datastore.edit {
            it[CHANNEL_KEY_PREFERENCE] = settings.channelKey
            it[PUSH_PREFERENCE] = settings.isPushEnabled
        }
    }
    override suspend fun getZendeskSettings(): Flow<ZendeskSettings> =
        context.datastore.data.map {
            ZendeskSettings(
                channelKey = it[CHANNEL_KEY_PREFERENCE].orEmpty(),
                isPushEnabled = it[PUSH_PREFERENCE] == true
            )
        }

    override suspend fun clearZendeskSettings() {
        context.datastore.edit {
            it.remove(CHANNEL_KEY_PREFERENCE)
            it.remove(PUSH_PREFERENCE)
        }
    }

    override suspend fun clearDataRepository() {
        context.datastore.edit {
            it.clear()
        }
    }
}