package com.biryulindevelop.unsplash.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.biryulindevelop.unsplash.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object SharedPreferencesUtils {
    fun createSharedPrefs(context: Context, tokenName: String): SharedPreferences {
        return context.getSharedPreferences(tokenName, Context.MODE_PRIVATE)
    }
}

fun SearchView.setChangeTextListener(block: (query: String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            block(newText)
            return false
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }
    })
}

fun ImageView.imgLoader(urls: String) {
    Glide.with(this)
        .load(urls)
        .error(R.drawable.baseline_error_24)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.placeholder)
        .into(this)
}
