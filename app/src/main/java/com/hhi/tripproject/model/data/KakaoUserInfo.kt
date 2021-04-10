package com.hhi.tripproject.model.data

class KakaoUserInfo {
    data class Response(
        val id: String,
        val connected_at: String,
        val kakao_account: Data
    )

    data class Data(
        val has_email: String,
        val email_needs_agreement: String,
        val email: String
    )
}