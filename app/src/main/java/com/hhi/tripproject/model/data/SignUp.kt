package com.hhi.tripproject.model.data

/**
 * provider : N(aver), K(akao), F(aceBook), G(oogle), S(tandard)
 * */
class SignUp{
    data class Response(
        val success: Boolean,
        val data: String,
        val message: String
    )
    data class Request(
        val email: String,
        val password: String,
        val provider: String,
        val token: String,
        val nickname: String
    )
}