package com.biryulindevelop.unsplash.data.api.dto.photo

import com.biryulindevelop.unsplash.data.db.entity.PhotoEntity
import com.biryulindevelop.unsplash.domain.model.Photo
import com.google.gson.annotations.SerializedName

data class PhotoDto(
    val id: String,
    val urls: UrlsDto,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    val likes: Int,
    val links: LinksDto,
    val user: UserDto,
    val height: Int,
    val width: Int
) {

    fun toPhoto(): Photo {
        return Photo(
            id = id,
            urlsSmall = urls.small,
            likedByUser = likedByUser,
            likes = likes,
            userName = user.name,
            userAvatar = user.profileImage.small,
            height = height,
            width = width
        )
    }

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