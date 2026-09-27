package com.example.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.crypto.KeystoreTokenCipher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

private const val GITHUB_TOKEN_KEY_ALIAS = "github_token_key"

class DefaultGithubTokenDataSource
@Inject
constructor(
    @Named(value = "githubToken") private val dataStore: DataStore<Preferences>,
) : GithubTokenDataSource {
    private val cipher = KeystoreTokenCipher(keyAlias = GITHUB_TOKEN_KEY_ALIAS)

    override val githubUsername: Flow<String?> =
        dataStore.data.map { preferences -> preferences[PreferencesKey.USERNAME_KEY] }

    override suspend fun saveToken(
        accessToken: String,
        username: String,
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ACCESS_TOKEN_KEY] =
                cipher.encrypt(plainText = accessToken)
            preferences[PreferencesKey.USERNAME_KEY] = username
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(key = PreferencesKey.ACCESS_TOKEN_KEY)
            preferences.remove(key = PreferencesKey.USERNAME_KEY)
        }
    }

    override suspend fun getAccessToken(): String? {
        val encrypted =
            dataStore.data.first()[PreferencesKey.ACCESS_TOKEN_KEY]
                ?: return null
        return cipher.decrypt(encoded = encrypted)
    }

    private object PreferencesKey {
        val ACCESS_TOKEN_KEY = stringPreferencesKey(name = "accessToken")
        val USERNAME_KEY = stringPreferencesKey(name = "username")
    }
}
