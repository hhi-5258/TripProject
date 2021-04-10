package com.hhi.tripproject.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hhi.tripproject.R
import com.hhi.tripproject.base.BaseActivity
import com.hhi.tripproject.databinding.ActivitySignUpBinding
import com.hhi.tripproject.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val vm: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = vm
        setObserver()
    }

    private fun setObserver(){
        vm.signUpSuccessEvent.observe(this, Observer {
            showToast("회원가입 완료")
            finish()
        })

        vm.signUpFailedEvent.observe(this, Observer {
            showToast(it)
        })
    }
}