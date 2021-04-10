package com.hhi.tripproject.model.data

class Login {
    data class Request(
        val email: String,
        val password: String
    )

    data class Response(
        val success: Boolean,
        val data: Data,
        val message: String
    )

    data class Data(
        val token: String,
        val refreshToken: String,
        val email: String,
        val useridx: Int,
        val nickname: String
    )
}