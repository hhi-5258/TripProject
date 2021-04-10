package com.hhi.tripproject.model.network

import com.hhi.tripproject.model.data.KakaoUserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface KakaoAPI {
    @GET("v2/user/me")
    fun getKakaoEmail(
        @Header("Authorization") authorization: String
    ): Call<KakaoUserInfo.Response>
}