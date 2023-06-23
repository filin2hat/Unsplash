package com.biryulindevelop.unsplash.data.api.dto.profile


import com.biryulindevelop.unsplash.domain.model.Profile
import com.google.gson.annotations.SerializedName

data class ProfileDto(
    val id: String,
    val username: String,
    val name: String?,
    val location: String?,
    @SerializedName("profile_image")
    val profileImage: ProfileImageDto,
    @SerializedName("total_likes")
    val totalLikes: Int
) {

    data class ProfileImageDto(
        val small: String,
        val medium: String,
        val large: String
    )

    fun toProfile(): Profile {
        return Profile(
            id = id,
            userName = username,
            name = name,
            location = location,
            avatar = profileImage.large,
            totalLikes = totalLikes
        )
    }
}