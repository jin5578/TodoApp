package com.example.profile.model

import com.example.designSystem.R as DesignSystemR

interface ProfileCategoryOption {
    val titleResId: Int
}

enum class ProfileTaskState(
    override val titleResId: Int,
    val isCompleted: Boolean,
) : ProfileCategoryOption {
    COMPLETED(
        titleResId = DesignSystemR.string.completed_tasks,
        isCompleted = true,
    ),
    PENDING(
        titleResId = DesignSystemR.string.pending_tasks,
        isCompleted = false,
    ),
}

enum class ProfileTaskDuration(
    override val titleResId: Int,
    val days: Long?,
) : ProfileCategoryOption {
    SEVEN_DAYS(
        titleResId = DesignSystemR.string.in_7_days,
        days = 7L,
    ),
    THIRTY_DAYS(
        titleResId = DesignSystemR.string.in_30_days,
        days = 30L,
    ),
    ALL(
        titleResId = DesignSystemR.string.all,
        days = null,
    ),
}
