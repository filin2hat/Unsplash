package com.biryulindevelop.unsplash.domain.dto.digest

import com.biryulindevelop.unsplash.domain.mapper.toListDigestTag
import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.domain.model.DigestTag
import com.google.gson.annotations.SerializedName

class DigestListDto : ArrayList<DigestDto>()

data class DigestDto(
    val id: String,
    val title: String,
    val description: String?,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    val tags: List<DigestTagDto>,
    val user: UserDto,
    @SerializedName("preview_photos")
    val previewPhotos: List<UserDto.PreviewPhotoDto>
) {

    data class DigestTagDto(
        val title: String
    ) {
        fun toDigestTag(): DigestTag = DigestTag(title)
    }

    fun toDigest(): Digest {
        return Digest(
            id = id,
            title = title,
            totalPhotos = totalPhotos,
            userUsername = user.username,
            userProfileImage = user.profileImage.small,
            previewPhoto = previewPhotos.random().urls.small,
            description = description,
            tags = tags.toListDigestTag()
        )
    }
}

data class UserDto(
    val id: String,
    val username: String,
    val name: String,
    @SerializedName("profile_image")
    val profileImage: ProfileImageDto
) {
    data class ProfileImageDto(
        val small: String,
        val medium: String,
        val large: String
    )

    data class UrlsDto(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
        @SerializedName("small_s3")
        val smallS3: String
    )

    data class PreviewPhotoDto(
        val id: String,
        val urls: UrlsDto
    )
}





