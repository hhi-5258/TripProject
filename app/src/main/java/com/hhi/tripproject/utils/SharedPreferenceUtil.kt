package com.hhi.tripproject.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.hhi.tripproject.global.App
import javax.inject.Inject

class SharedPreferenceUtil @Inject constructor() {
    private val preference: SharedPreferences = App.context.getSharedPreferences(FILE_NAME, 0)

    fun setString(value: String, key: String) {
        preference.edit { putString(key, value) }
    }

    fun getString(key: String): String {
        return preference.getString(key, null) ?: ""
    }

    companion object {
        private const val FILE_NAME = "prefs"
    }
}