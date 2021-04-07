package com.hhi.tripproject.model.local

import android.util.Log
import com.hhi.tripproject.utils.SharedPreferenceUtil
import javax.inject.Inject

class LoginLocalDataSourceImpl @Inject constructor(
    private val sharedPreference: SharedPreferenceUtil
) : LoginLocalDataSource {

    override fun saveToken(token: String) {
        sharedPreference.setString(token, TOKEN_KEY)
        Log.d("saveToken", token)
    }

    override fun getToken(): String {
        return sharedPreference.getString(TOKEN_KEY)
    }

    companion object {
        private const val TOKEN_KEY = "pref_token"
    }
}