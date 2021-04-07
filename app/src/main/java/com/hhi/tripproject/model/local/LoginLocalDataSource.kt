package com.hhi.tripproject.model.local

interface LoginLocalDataSource {
    fun saveToken(token: String)
    fun getToken(): String
}