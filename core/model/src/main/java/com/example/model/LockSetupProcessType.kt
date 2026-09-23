package com.example.model

enum class LockSetupProcessType {
    ENTER_EXISTING_PASSWORD, // 1. 이전 비밀번호 입력
    EXISTING_PASSWORD_MISMATCHED, // 2. 이전 비밀번호랑 다름
    ENTER_NEW_PASSWORD, // 3. 새로운 비밀번호 입력
    CONFIRM_NEW_PASSWORD, // 4. 새로운 비밀번호 재입력
    CONFIRM_NEW_PASSWORD_MISMATCHED, // 5. 새로운 비밀번호 재입력 다름
    UNLOCK_PASSWORD, // 6. 비밀번호 설정 해제
}
