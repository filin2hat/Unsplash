package com.biryulindevelop.unsplash.data.api.dto.photo

import com.biryulindevelop.unsplash.domain.model.PhotoDetails
import com.biryulindevelop.unsplash.tools.toListTag
import com.google.gson.annotations.SerializedName

data class PhotoDetailsDto(
    val id: String,
    val downloads: Int,
    val likes: Int,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    val exif: ExifDto,
    val location: LocationDto,
    val tags: List<TagDto>,
    val urls: UrlsDto,
    val links: LinksDto,
    val user: UserDto
) {
    fun toPhotoDetails(): PhotoDetails {
        return PhotoDetails(
            id = id,
            downloads = downloads,
            likedByUser = likedByUser,
            likes = likes,
            exif = exif.toPhotoDetailsExif(),
            location = location.toPhotoDetailsLocation(),
            tags = tags.toListTag(),
            urls = urls.toPhotoDetailsUrls(),
            user = user.toPhotoDetailsUser()
        )
    }
}
