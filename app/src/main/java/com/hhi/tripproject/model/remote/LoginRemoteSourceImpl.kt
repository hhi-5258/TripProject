package com.hhi.tripproject.model.remote

import com.hhi.tripproject.model.data.Login
import com.hhi.tripproject.model.data.SignUp
import com.hhi.tripproject.model.network.ServerAPI
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class LoginRemoteSourceImpl @Inject constructor(
    private val api: ServerAPI
) : LoginRemoteSource {

    override fun login(
        body: Login.Request,
        success: (Login.Response) -> Unit,
        failed: (Throwable) -> Unit
    ) {
        api.login(body).enqueue(object : retrofit2.Callback<Login.Response> {
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
        api.signUp(body).enqueue(object : retrofit2.Callback<SignUp.Response> {
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

}