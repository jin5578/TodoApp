package com.example.model.memo

import java.time.LocalDateTime

data class Memo(
    val memoTitle: String,
    val memoContent: String,
    val memoUpdatedAt: LocalDateTime?,
    val memoSystem: MemoSystem,
)
