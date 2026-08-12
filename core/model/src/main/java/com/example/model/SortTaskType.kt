package com.example.model

enum class SortTaskType(val key: String, val title: String) {
    BY_PRIORITY_ASCENDING(
        key = "byPriorityAscending",
        title = "Priority (Low to High)"
    ),
    BY_PRIORITY_DESCENDING(
        key = "byPriorityDescending",
        title = "Priority (High to Low)"
    ),
    BY_TIME_ASCENDING(
        key = "byTimeAscending",
        title = "Time (Latest at Bottom)"
    ),
    BY_TIME_DESCENDING(
        key = "byTimeDescending",
        title = "Time (Latest at Top)"
    ),
    BY_CREATE_TIME_ASCENDING(
        key = "byCreateTimeAscending",
        title = "Creation Time (Latest at Bottom)"
    ),
    BY_CREATE_TIME_DESCENDING(
        key = "byCreateTimeDescending",
        title = "Creation Time (Latest at Top)"
    ),
}