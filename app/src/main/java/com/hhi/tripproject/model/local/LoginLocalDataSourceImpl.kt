package com.hhi.tripproject.model.local

import com.hhi.tripproject.utils.SharedPreferenceUtil
import javax.inject.Inject

class LoginLocalDataSourceImpl @Inject constructor(
    private val sharedPreference: SharedPreferenceUtil
) : LoginLocalDataSource {

    override fun saveToken(token: String) {
        sharedPreference.setString(token, TOKEN_KEY)
    }

    override fun getToken(): String {
        return sharedPreference.getString(TOKEN_KEY)
    }

    override fun saveUserIdx(userIdx: Int) {
        sharedPreference.setInt(userIdx, USERIDX_KEY)
    }

    override fun getUserIdx(): Int {
        return sharedPreference.getInt(USERIDX_KEY)
    }

    companion object {
        private const val TOKEN_KEY = "pref_token"
        private const val USERIDX_KEY = "pref_useridx"
    }
}