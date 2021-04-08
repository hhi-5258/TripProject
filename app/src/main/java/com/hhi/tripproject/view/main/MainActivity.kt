package com.hhi.tripproject.view.main

import android.os.Bundle
import androidx.activity.viewModels
import com.hhi.tripproject.R
import com.hhi.tripproject.base.BaseActivity
import com.hhi.tripproject.databinding.ActivityMainBinding
import com.hhi.tripproject.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val vm: MainViewModel by viewModels()
    private val adapter = MainRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = vm

        setUpUi()

//        vm.getTourList(vm.getUserIdx())
        vm.getTourList(1)

    }

    private fun setUpUi() {
        binding.mainRecycler.setHasFixedSize(false)
        binding.mainRecycler.adapter = adapter
    }
}