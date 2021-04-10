package com.hhi.tripproject.model.data

class NaverUserInfo {
    data class Response(
        val resultcode: String,
        val message: String,
        val response: Data
    )

    data class Data(
        val id: String,
        val email: String
    )

}