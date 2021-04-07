package com.hhi.tripproject.model.repository

import com.hhi.tripproject.model.data.Login
import com.hhi.tripproject.model.data.SignUp
import com.hhi.tripproject.model.local.LoginLocalDataSourceImpl
import com.hhi.tripproject.model.remote.LoginRemoteSourceImpl
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteSourceImpl,
    private val loginLocalDataSource: LoginLocalDataSourceImpl
) : LoginRepository {
    override fun login(
        body: Login.Request,
        success: (Login.Response) -> Unit,
        failed: (Throwable) -> Unit
    ) {
        loginRemoteDataSource.login(body, success, failed)
    }

    override fun signUp(
        body: SignUp.Request,
        success: (SignUp.Response) -> Unit,
        failed: (Throwable) -> Unit
    ) {
        loginRemoteDataSource.signUp(body, success, failed)
    }

    override fun saveToken(token: String) {
        loginLocalDataSource.saveToken(token)
    }

    override fun getToken(): String {
        return loginLocalDataSource.getToken()
    }


}