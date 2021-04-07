package com.hhi.tripproject.model.repository

import com.hhi.tripproject.model.data.Login
import com.hhi.tripproject.model.data.SignUp

interface LoginRepository {
    fun login(
        body: Login.Request,
        success: (Login.Response) -> Unit,
        failed: (Throwable) -> Unit
    )

    fun signUp(
        body: SignUp.Request,
        success: (SignUp.Response) -> Unit,
        failed: (Throwable) -> Unit
    )

    fun saveToken(token: String)

    fun getToken() : String
}