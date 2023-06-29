package com.biryulindevelop.unsplash.presentation.utils

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
            return false
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            block(query)
            this@setChangeTextListener.clearFocus()
            this@setChangeTextListener.setQuery(null, false)
            return true
        }
    })

    this.setOnSearchClickListener { this.requestFocus() }
}

fun ImageView.imgLoader(urls: String) {
    Glide.with(this)
        .load(urls)
        .error(R.drawable.baseline_error_24)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.placeholder)
        .into(this)
}
