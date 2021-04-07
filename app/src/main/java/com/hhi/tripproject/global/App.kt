package com.hhi.tripproject.global

import android.app.Application
import android.content.Context
import com.hhi.tripproject.BuildConfig
import com.hhi.tripproject.utils.SharedPreferenceUtil
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltAndroidApp
class App : Application () {
    override fun onCreate() {
        super.onCreate()

        context = this
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

    companion object {
        lateinit var context: Application
    }
}
