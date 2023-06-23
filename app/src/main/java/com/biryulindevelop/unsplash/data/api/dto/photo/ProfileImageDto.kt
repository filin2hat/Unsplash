package com.biryulindevelop.unsplash.data.api.dto.photo

import com.biryulindevelop.unsplash.domain.model.ProfileImage

data class ProfileImageDto(
    val large: String,
    val medium: String,
    val small: String
) {
    fun toPhotoDetailsProfileImage(): ProfileImage {
        return ProfileImage(small)
    }
}