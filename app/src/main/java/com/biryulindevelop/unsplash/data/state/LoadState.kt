package com.biryulindevelop.unsplash.data.state

enum class LoadState(
    var message: String = ""
) {
    START,
    LOADING,
    ERROR,
    SUCCESS
}