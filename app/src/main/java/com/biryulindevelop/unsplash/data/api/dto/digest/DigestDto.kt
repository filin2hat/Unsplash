package com.biryulindevelop.unsplash.data.api.dto.digest


import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.tools.toListDigestTag
import com.google.gson.annotations.SerializedName

data class DigestDto(
    val id: String,
    val title: String,
    val description: String?,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    val tags: List<com.biryulindevelop.unsplash.data.api.dto.digest.DigestTagDto>,
    val user: com.biryulindevelop.unsplash.data.api.dto.digest.UserDto,
    @SerializedName("preview_photos")
    val previewPhotos: List<com.biryulindevelop.unsplash.data.api.dto.digest.PreviewPhotoDto>
) {
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