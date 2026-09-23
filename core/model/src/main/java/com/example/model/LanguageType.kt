package com.example.model

enum class LanguageType(
    val key: String,
    val title: String,
    val languageTag: String,
) {
    KOREAN(
        key = "korean",
        title = "한국어",
        languageTag = "ko",
    ),
    ENGLISH(
        key = "english",
        title = "English",
        languageTag = "en",
    ),
}
