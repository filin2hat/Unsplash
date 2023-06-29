package com.biryulindevelop.unsplash.domain.dto.photo

import com.biryulindevelop.unsplash.domain.model.ProfileImage
import com.biryulindevelop.unsplash.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    val bio: String?,
    val name: String,
    @SerializedName("profile_image") val profileImage: ProfileImageDto,
    val username: String
) {

    data class ProfileImageDto(
        val large: String,
        val medium: String,
        val small: String
    ) {
        fun toPhotoDetailsProfileImage(): ProfileImage {
            return ProfileImage(small)
        }
    }

    fun toPhotoDetailsUser(): User {
        return User(
            bio, name, profileImage.toPhotoDetailsProfileImage(), username
        )
    }
}