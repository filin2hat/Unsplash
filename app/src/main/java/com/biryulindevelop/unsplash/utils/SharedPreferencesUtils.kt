package com.biryulindevelop.unsplash.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtils {
    fun createSharedPrefs(context: Context, tokenName: String): SharedPreferences {
        return context.getSharedPreferences(tokenName, Context.MODE_PRIVATE)
    }
}