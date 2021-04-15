package com.hhi.tripproject.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hhi.tripproject.base.BaseViewModel
import com.hhi.tripproject.model.data.TourList
import com.hhi.tripproject.model.repository.LoginRepositoryImpl
import com.hhi.tripproject.utils.SingleLiveEvent

class MainViewModel @ViewModelInject constructor(
    private val loginRepositoryImpl: LoginRepositoryImpl
) : BaseViewModel() {
    private val _tourList = MutableLiveData<ArrayList<TourList.TourData>>()
    val tourList: LiveData<ArrayList<TourList.TourData>> get() = _tourList

    fun getTourList(useridx: Int) {
        loginRepositoryImpl.getTourList(
            body = TourList.Request(useridx = useridx),
            success = {
                if (it.success) {
                    _tourList.value = it.data.tourlist
                } else {
                    Log.e("get_tourList_failed", it.message)
                }
            },
            failed = {
                Log.e("get_tourList_failed", it.toString())
            }
        )
    }

    fun getUserIdx() : Int {
        return loginRepositoryImpl.getUserIdx()
    }
}