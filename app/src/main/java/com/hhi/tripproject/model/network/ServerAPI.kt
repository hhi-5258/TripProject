package com.hhi.tripproject.model.network

import com.hhi.tripproject.model.data.Login
import com.hhi.tripproject.model.data.SignUp
import com.hhi.tripproject.model.data.TourList
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

    @POST("itstudy/tourlist.php")
    fun getTourList(
        @Body body: TourList.Request
    ): Call<TourList.Response>
}