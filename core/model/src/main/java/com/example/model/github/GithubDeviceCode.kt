package com.example.model.github

data class GithubDeviceCode(
    val deviceCode: String,
    val userCode: String,
    val verificationUri: String,
    val expiresInSeconds: Int,
    val intervalSeconds: Int,
)
