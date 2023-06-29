package com.biryulindevelop.domain.dto.photo

import com.biryulindevelop.domain.entity.PhotoEntity
import com.google.gson.annotations.SerializedName

data class PhotoDto(
    val id: String,
    val urls: UrlsDto,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    val likes: Int,
    val links: PhotoDetailsDto.LinksDto,
    val user: UserDto,
    val height: Int,
    val width: Int
) {

    fun toPhotoEntity(): PhotoEntity {
        return PhotoEntity(
            photoId = id,
            smallUrls = urls.small,
            likedByUser = likedByUser,
            counterLikes = likes,
            userName = user.name,
            profileImage = user.profileImage.small
        )
    }
}