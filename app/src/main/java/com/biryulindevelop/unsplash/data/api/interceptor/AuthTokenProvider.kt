package com.biryulindevelop.unsplash.data.api.interceptor

import android.content.Context
import com.biryulindevelop.unsplash.data.api.TOKEN_SHARED_KEY
import com.biryulindevelop.unsplash.data.api.TOKEN_SHARED_NAME

class AuthTokenProvider(private val context: Context) {

    fun getToken(): String? {
        val pref = context.getSharedPreferences(TOKEN_SHARED_NAME, Context.MODE_PRIVATE)

        return pref.getString(TOKEN_SHARED_KEY, "")
    }

}