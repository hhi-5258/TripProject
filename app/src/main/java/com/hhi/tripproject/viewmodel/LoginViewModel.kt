package com.hhi.tripproject.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hhi.tripproject.base.BaseViewModel
import com.hhi.tripproject.model.data.Login
import com.hhi.tripproject.model.data.SignUp
import com.hhi.tripproject.model.repository.LoginRepositoryImpl
import com.hhi.tripproject.utils.SingleLiveEvent

class LoginViewModel @ViewModelInject constructor(
    private val loginRepositoryImpl: LoginRepositoryImpl
) : BaseViewModel() {
    private val _signUpClickEvent = SingleLiveEvent<Unit>()
    val signUpClickEvent: LiveData<Unit> get() = _signUpClickEvent
    private val _naverLoginClickEvent = SingleLiveEvent<Unit>()
    val naverLoginClickEvent: LiveData<Unit> get() = _naverLoginClickEvent
    private val _kakaoLoginClickEvent = SingleLiveEvent<Unit>()
    val kakaoLoginClickEvent: LiveData<Unit> get() = _kakaoLoginClickEvent
    private val _facebookLoginClickEvent = SingleLiveEvent<Unit>()
    val facebookLoginClickEvent: LiveData<Unit> get() = _facebookLoginClickEvent
    private val _loginSuccessEvent = SingleLiveEvent<Unit>()
    val loginSuccessEvent: LiveData<Unit> get() = _loginSuccessEvent
    private val _loginFailedEvent = SingleLiveEvent<String>()
    val loginFailedEvent: LiveData<String> get() = _loginFailedEvent
    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    fun login() {
        loginRepositoryImpl.login(
            Login.Request(email = id.value!!, password = pw.value!!),
            success = {
                _loginSuccessEvent.call()
                loginRepositoryImpl.saveToken(it.data.token)
                loginRepositoryImpl.saveUserIdx(it.data.useridx)
            },
            failed = {
                _loginFailedEvent.value = it.toString()
                Log.e("login_failed", it.toString())
            }
        )
    }

    fun saveTokenToServer(token: String, provider: String, email: String) {
        loginRepositoryImpl.signUp(
            SignUp.Request(
                email = email,
                password = "1234",
                provider = provider,
                token = token,
                nickname = ""
            ),
            success = {
                if (it.success) {
                    _loginSuccessEvent.call()
                    loginRepositoryImpl.saveToken(token)
                    loginRepositoryImpl.saveUserIdx(it.data.useridx)
                } else {
                    _loginFailedEvent.value = it.message
                    Log.e("signup_failed", it.message)
                }
            },
            failed = {
                _loginFailedEvent.value = it.toString()
                Log.e("signup_failed", it.toString())
            }
        )
    }

    fun getNaverEmail(token: String) {
        loginRepositoryImpl.getNaverEmail(
            authorization = "Bearer $token",
            success = {
                if (it.message == "success"){
                    saveTokenToServer(token, "N", it.response.email)
                } else {
                    _loginFailedEvent.value = it.message
                    Log.e("get_naver_email", it.toString())
                }
            },
            failed = {
                _loginFailedEvent.value = it.toString()
                Log.e("get_naver_email", it.toString())
            }
        )
    }

    fun isLoggedIn() : Boolean {
        return loginRepositoryImpl.getToken().isNotEmpty()
    }

    fun signUp() {
        _signUpClickEvent.call()
    }

    fun kakaoLogin() {
        _kakaoLoginClickEvent.call()
    }

    fun naverLogin() {
        _naverLoginClickEvent.call()
    }

    fun facebookLogin() {
        _facebookLoginClickEvent.call()
    }

}