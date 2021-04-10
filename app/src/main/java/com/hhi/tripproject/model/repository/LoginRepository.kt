package com.hhi.tripproject.model.repository

import com.hhi.tripproject.model.data.*

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

    fun getNaverEmail(
        authorization: String,
        success: (NaverUserInfo.Response) -> Unit,
        failed: (Throwable) -> Unit
    )

    fun getKakaoEmail(
        authorization: String,
        success: (KakaoUserInfo.Response) -> Unit,
        failed: (Throwable) -> Unit
    )

    fun saveToken(token: String)

    fun getToken(): String

    fun saveUserIdx(userIdx: Int)

    fun getUserIdx(): Int
}