package com.biryulindevelop.unsplash.data.api.dto.photo

import com.biryulindevelop.unsplash.domain.model.Exif

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