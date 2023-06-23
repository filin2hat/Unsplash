package com.biryulindevelop.unsplash.data.api.dto.photo

import com.biryulindevelop.unsplash.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    val bio: String?,
    val name: String,
    @SerializedName("profile_image") val profileImage: ProfileImageDto,
    val username: String
) {
    fun toPhotoDetailsUser(): User {
        return User(
            bio, name, profileImage.toPhotoDetailsProfileImage(), username
        )
    }
}