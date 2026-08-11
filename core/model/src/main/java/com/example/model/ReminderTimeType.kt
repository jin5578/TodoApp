package com.example.model

enum class ReminderTimeType(
    val title: String,
    val subtitle: String,
    val minutesBefore: Int,
) {
    ON_TIME(
        title = "On time",
        subtitle = "On",
        minutesBefore = 0
    ),
    TEN_MINUTES_BEFORE(
        title = "10 min before",
        subtitle = "-10m",
        minutesBefore = 10,
    ),
    THIRTY_MINUTES_BEFORE(
        title = "30 min before",
        subtitle = "-30m",
        minutesBefore = 30,
    ),
    ONE_HOUR_BEFORE(
        title = "1 hour before",
        subtitle = "-1h",
        minutesBefore = 60,
    )
}