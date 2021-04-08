package com.hhi.tripproject.model.data

/**
 * provider : N(aver), K(akao), F(aceBook), G(oogle), S(tandard)
 * */
class SignUp{
    data class Response(
        val success: Boolean,
        val data: Data,
        val message: String
    )

    data class Data(
        val email: String,
        val useridx: Int
    )

    data class Request(
        val email: String,
        val password: String,
        val provider: String,
        val token: String,
        val nickname: String
    )
}