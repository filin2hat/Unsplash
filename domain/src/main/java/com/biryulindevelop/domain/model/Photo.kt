package com.biryulindevelop.domain.model

data class Photo(
    val id: String,
    val urlsSmall: String,
    val likedByUser: Boolean,
    val likes: Int,
    var isLikeProgress: Boolean = false,
    val userName: String,
    val userAvatar: String,
    val height: Int,
    val width: Int
)
