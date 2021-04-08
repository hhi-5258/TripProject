package com.hhi.tripproject.model.local

interface LoginLocalDataSource {
    fun saveToken(token: String)
    fun getToken(): String
    fun saveUserIdx(userIdx: Int)
    fun getUserIdx(): Int
}