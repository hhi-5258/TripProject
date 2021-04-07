package com.hhi.tripproject.model.network

import com.hhi.tripproject.model.data.Login
import com.hhi.tripproject.model.data.SignUp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ServerAPI {
    @POST("itstudy/login.php")
    fun login(
        @Body body: Login.Request
    ): Call<Login.Response>

    @POST("itstudy/signup.php")
    fun signUp(
        @Body body: SignUp.Request
    ): Call<SignUp.Response>
}