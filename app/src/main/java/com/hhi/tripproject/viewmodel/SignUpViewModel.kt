package com.hhi.tripproject.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import com.hhi.tripproject.base.BaseViewModel
import com.hhi.tripproject.model.data.SignUp
import com.hhi.tripproject.model.repository.LoginRepositoryImpl
import com.hhi.tripproject.utils.SingleLiveEvent

class SignUpViewModel @ViewModelInject constructor(
    private val loginRepositoryImpl: LoginRepositoryImpl
) : BaseViewModel() {
    private val _signUpSuccessEvent = SingleLiveEvent<Unit>()
    val signUpSuccessEvent: LiveData<Unit> = _signUpSuccessEvent
    private val _signUpFailedEvent = SingleLiveEvent<String>()
    val signUpFailedEvent: LiveData<String> = _signUpFailedEvent
    val id = SingleLiveEvent<String>()
    val pw = SingleLiveEvent<String>()
    val pwCheck = SingleLiveEvent<String>()

    fun signUp() {
        if(pw.value.equals(pwCheck.value)) {
            loginRepositoryImpl.signUp(
                SignUp.Request(
                    email = id.value!!,
                    password = pw.value!!,
                    provider = "S",
                    token = "",
                    nickname = ""
                ),
                success = {
                    if (it.success) {
                        _signUpSuccessEvent.call()
                    } else {
                        _signUpFailedEvent.value = it.message
                        Log.e("signup_failed", it.message)
                    }
                },
                failed = {
                    Log.e("signup_failed", it.toString())
                }
            )
        } else {
            _signUpFailedEvent.value = "비밀번호가 일치하지 않습니다."
        }
    }
}