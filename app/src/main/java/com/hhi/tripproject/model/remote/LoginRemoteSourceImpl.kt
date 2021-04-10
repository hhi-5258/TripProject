package com.hhi.tripproject.model.remote

import com.hhi.tripproject.model.data.*
import com.hhi.tripproject.model.network.KakaoAPI
import com.hhi.tripproject.model.network.NaverAPI
import com.hhi.tripproject.model.network.ServerAPI
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class LoginRemoteSourceImpl @Inject constructor(
    private val serverApi: ServerAPI,
    private val naverApi: NaverAPI,
    private val kakaoApi: KakaoAPI
) : LoginRemoteSource {

    override fun login(
        body: Login.Request,
        success: (Login.Response) -> Unit,
        failed: (Throwable) -> Unit
    ) {
        serverApi.login(body).enqueue(object : retrofit2.Callback<Login.Response> {
            override fun onResponse(
                call: Call<Login.Response>,
                response: Response<Login.Response>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                }
            }

            override fun onFailure(call: Call<Login.Response>, t: Throwable) {
                failed(t)
            }
        })
    }

    override fun signUp(
        body: SignUp.Request,
        success: (SignUp.Response) -> Unit,
        failed: (Throwable) -> Unit
    ) {
        serverApi.signUp(body).enqueue(object : retrofit2.Callback<SignUp.Response> {
            override fun onResponse(
                call: Call<SignUp.Response>,
                response: Response<SignUp.Response>
            ) {
                response.body()?.let { success(it) }
            }

            override fun onFailure(call: Call<SignUp.Response>, t: Throwable) {
                failed(t)
            }
        })
    }

    override fun getTourList(
        body: TourList.Request,
        success: (TourList.Response) -> Unit,
        failed: (Throwable) -> Unit
    ) {
        serverApi.getTourList(body).enqueue(object : retrofit2.Callback<TourList.Response> {
            override fun onResponse(
                call: Call<TourList.Response>,
                response: Response<TourList.Response>
            ) {
                response.body()?.let { success(it) }
            }
            override fun onFailure(call: Call<TourList.Response>, t: Throwable) {
                failed(t)
            }
        })
    }

    override fun getNaverEmail(
        authorization: String,
        success: (NaverUserInfo.Response) -> Unit,
        failed: (Throwable) -> Unit
    ) {
        naverApi.getNaverEmail(authorization).enqueue(object : retrofit2.Callback<NaverUserInfo.Response> {
            override fun onResponse(
                call: Call<NaverUserInfo.Response>,
                response: Response<NaverUserInfo.Response>
            ) {
                response.body()?.let {
                    success(it)
                }
            }
            override fun onFailure(call: Call<NaverUserInfo.Response>, t: Throwable) {
                failed(t)
            }
        })
    }

    override fun getKakaoEmail(
        authorization: String,
        success: (KakaoUserInfo.Response) -> Unit,
        failed: (Throwable) -> Unit
    ) {
        kakaoApi.getKakaoEmail(authorization).enqueue(object : retrofit2.Callback<KakaoUserInfo.Response> {
            override fun onResponse(
                call: Call<KakaoUserInfo.Response>,
                response: Response<KakaoUserInfo.Response>
            ) {
                response.body()?.let {
                    success(it)
                }
            }

            override fun onFailure(call: Call<KakaoUserInfo.Response>, t: Throwable) {
                failed(t)
            }
        })
    }
}