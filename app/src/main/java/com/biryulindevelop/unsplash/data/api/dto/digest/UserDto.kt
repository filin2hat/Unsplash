package com.biryulindevelop.unsplash.data.api.dto.digest


import com.google.gson.annotations.SerializedName

data class UserDto(
    val id: String,
    val username: String,
    val name: String,
    @SerializedName("profile_image")
    val profileImage: ProfileImageDto
)