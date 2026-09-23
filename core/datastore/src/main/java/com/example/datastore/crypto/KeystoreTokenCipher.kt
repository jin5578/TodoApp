package com.example.datastore.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

private const val ANDROID_KEYSTORE = "AndroidKeyStore"
private const val TRANSFORMATION = "AES/GCM/NoPadding"
private const val GCM_TAG_LENGTH_BITS = 128
private const val IV_SIZE_BYTES = 12

class KeystoreTokenCipher(
    private val keyAlias: String,
) {
    private val keyStore =
        KeyStore
            .getInstance(ANDROID_KEYSTORE)
            .apply { load(null) }

    private val secretKey: SecretKey
        get() =
            (
                keyStore.getEntry(
                    keyAlias,
                    null,
                ) as? KeyStore.SecretKeyEntry
                )?.secretKey
                ?: generateKey()

    private fun generateKey(): SecretKey {
        val keyGenerator =
            KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                ANDROID_KEYSTORE,
            )
        val spec =
            KeyGenParameterSpec
                .Builder(
                    keyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
                ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build()
        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    fun encrypt(plainText: String): String {
        val cipher =
            Cipher.getInstance(TRANSFORMATION).apply {
                init(Cipher.ENCRYPT_MODE, secretKey)
            }
        val cipherBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        val combined = cipher.iv + cipherBytes
        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    fun decrypt(encoded: String): String {
        val combined = Base64.decode(encoded, Base64.NO_WRAP)
        val iv = combined.copyOfRange(0, IV_SIZE_BYTES)
        val cipherBytes = combined.copyOfRange(IV_SIZE_BYTES, combined.size)
        val cipher =
            Cipher.getInstance(TRANSFORMATION).apply {
                init(
                    Cipher.DECRYPT_MODE,
                    secretKey,
                    GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv),
                )
            }
        return String(cipher.doFinal(cipherBytes), Charsets.UTF_8)
    }
}
