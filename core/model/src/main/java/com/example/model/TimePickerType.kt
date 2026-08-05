package com.example.model

/*
parameter
val key: String
val title: String
val subtitle: String
val value(데이터에 따른 네이밍): T
*/
enum class TimePickerType(
    val key: String,
    val title: String
) {
    SCROLL_TIME_PICKER(
        key = "scrollTimePicker", title = "Scroll picker"
    ),
    CLOCK_TIME_PICKER(
        key = "clockTimePicker", title = "Clock picker"
    )
}