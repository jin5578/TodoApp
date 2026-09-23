package com.example.model

enum class ThemeType(
    val key: String,
    val title: String,
) {
    SYSTEM(key = "system", title = "System"),
    SUN_RISE(key = "sunRise", title = "SunRise"),
    OCEAN(key = "ocean", title = "Ocean"),
    MEADOW(key = "meadow", title = "Meadow"),
    MIDNIGHT(key = "midnight", title = "Midnight"),
    DEEP_SPACE(key = "deepSpace", title = "DeepSpace"),
    EMBER(key = "ember", title = "Ember"),
}
