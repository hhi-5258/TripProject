package com.hhi.tripproject.model.network

import com.hhi.tripproject.model.data.NaverUserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface NaverAPI {
    @GET("v1/nid/me")
    fun getNaverEmail(
        @Header("Authorization") authorization: String
    ): Call<NaverUserInfo.Response>
}