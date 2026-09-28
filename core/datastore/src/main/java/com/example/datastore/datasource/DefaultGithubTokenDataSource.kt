package com.example.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.crypto.KeystoreTokenCipher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

private const val GITHUB_TOKEN_KEY_ALIAS = "github_token_key"
private const val EXPIRY_SAFETY_MARGIN_MILLIS = 60_000L

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
        refreshToken: String?,
        accessTokenExpiresInSeconds: Int?,
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ACCESS_TOKEN_KEY] =
                cipher.encrypt(plainText = accessToken)
            preferences[PreferencesKey.USERNAME_KEY] =
                username
            applyRefreshToken(preferences, refreshToken)
            applyExpiresAt(preferences, accessTokenExpiresInSeconds)
        }
    }

    override suspend fun updateAccessToken(
        accessToken: String,
        refreshToken: String?,
        accessTokenExpiresInSeconds: Int?,
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ACCESS_TOKEN_KEY] =
                cipher.encrypt(plainText = accessToken)
            applyRefreshToken(preferences, refreshToken)
            applyExpiresAt(preferences, accessTokenExpiresInSeconds)
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(key = PreferencesKey.ACCESS_TOKEN_KEY)
            preferences.remove(key = PreferencesKey.USERNAME_KEY)
            preferences.remove(key = PreferencesKey.REFRESH_TOKEN_KEY)
            preferences.remove(key = PreferencesKey.EXPIRES_AT_KEY)
        }
    }

    override suspend fun getAccessToken(): String? {
        val encrypted =
            dataStore.data.first()[PreferencesKey.ACCESS_TOKEN_KEY]
                ?: return null
        return cipher.decrypt(encoded = encrypted)
    }

    override suspend fun getRefreshToken(): String? {
        val encrypted =
            dataStore.data.first()[PreferencesKey.REFRESH_TOKEN_KEY]
                ?: return null
        return cipher.decrypt(encoded = encrypted)
    }

    override suspend fun isAccessTokenExpired(): Boolean {
        val expiresAt =
            dataStore.data.first()[PreferencesKey.EXPIRES_AT_KEY]
                ?: return false
        return System.currentTimeMillis() >= expiresAt - EXPIRY_SAFETY_MARGIN_MILLIS
    }

    private fun applyRefreshToken(
        preferences: MutablePreferences,
        refreshToken: String?,
    ) {
        if (refreshToken != null) {
            preferences[PreferencesKey.REFRESH_TOKEN_KEY] =
                cipher.encrypt(plainText = refreshToken)
        }
    }

    private fun applyExpiresAt(
        preferences: MutablePreferences,
        accessTokenExpiresInSeconds: Int?,
    ) {
        if (accessTokenExpiresInSeconds != null) {
            preferences[PreferencesKey.EXPIRES_AT_KEY] =
                System.currentTimeMillis() + accessTokenExpiresInSeconds * 1000L
        } else {
            preferences.remove(key = PreferencesKey.EXPIRES_AT_KEY)
        }
    }

    private object PreferencesKey {
        val ACCESS_TOKEN_KEY = stringPreferencesKey(name = "accessToken")
        val USERNAME_KEY = stringPreferencesKey(name = "username")
        val REFRESH_TOKEN_KEY = stringPreferencesKey(name = "refreshToken")
        val EXPIRES_AT_KEY = longPreferencesKey(name = "accessTokenExpiresAt")
    }
}
