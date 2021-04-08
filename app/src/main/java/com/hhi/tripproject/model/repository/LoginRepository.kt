package com.hhi.tripproject.model.repository

import com.hhi.tripproject.model.data.Login
import com.hhi.tripproject.model.data.SignUp
import com.hhi.tripproject.model.data.TourList

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

    fun getTourList(
        body: TourList.Request,
        success: (TourList.Response) -> Unit,
        failed: (Throwable) -> Unit
    )

    fun saveToken(token: String)

    fun getToken() : String

    fun saveUserIdx(userIdx: Int)

    fun getUserIdx() : Int
}