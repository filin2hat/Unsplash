package com.biryulindevelop.unsplash.domain.state

enum class LoadState(
    var message: String = ""
) {
    START,
    LOADING,
    ERROR,
    SUCCESS
}