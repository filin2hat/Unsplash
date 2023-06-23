package com.biryulindevelop.unsplash.data.api.dto.photo

import com.biryulindevelop.unsplash.domain.model.Exif
import com.biryulindevelop.unsplash.domain.model.Location
import com.biryulindevelop.unsplash.domain.model.PhotoDetails
import com.biryulindevelop.unsplash.domain.model.Position
import com.biryulindevelop.unsplash.utils.toListTag
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
    data class LocationDto(
        val city: String?,
        val country: String?,
        val position: PositionDto
    ) {

        data class PositionDto(
            val latitude: Double?,
            val longitude: Double?
        ) {
            fun toPhotoDetailsPosition(): Position {
                return Position(latitude, longitude)
            }
        }

        fun toPhotoDetailsLocation(): Location {
            return Location(city, position.toPhotoDetailsPosition())
        }
    }

    data class LinksDto(
        val download: String,
        val downloadLocation: String,
        val html: String,
        val self: String
    )

    data class ExifDto(
        val make: String?,
        val model: String?,
        val name: String?,
        val exposureTime: String?,
        val aperture: String?,
        val focalLength: String?,
        val iso: Int?
    ) {
        fun toPhotoDetailsExif(): Exif {
            return Exif(make, model, model, exposureTime, aperture, focalLength, iso)
        }
    }

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
