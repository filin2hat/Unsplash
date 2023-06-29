package com.biryulindevelop.data.api.authentication

import android.content.Context
import com.biryulindevelop.common.TOKEN_KEY
import com.biryulindevelop.common.TOKEN_NAME

class AuthTokenProvider(
    private val context: Context
) {
    fun getToken(): String? {
        val pref = context.getSharedPreferences(TOKEN_NAME, Context.MODE_PRIVATE)

        return pref.getString(TOKEN_KEY, "")
    }
}
