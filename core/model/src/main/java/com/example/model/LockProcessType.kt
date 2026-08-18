package com.example.model

enum class LockProcessType {
    ENTER_EXISTING_PASSWORD,         // 1. 이전 비밀번호 입력
    EXISTING_PASSWORD_MISMATCHED,    // 2. 이전 비밀번호랑 다름
}