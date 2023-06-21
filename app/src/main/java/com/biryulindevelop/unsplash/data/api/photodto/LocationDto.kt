package com.biryulindevelop.unsplash.data.api.photodto

import com.biryulindevelop.unsplash.domain.model.Location

data class LocationDto(
    val city: String?,
    val country: String?,
    val position: PositionDto
) {
    fun toPhotoDetailsLocation() = Location(city, position.toPhotoDetailsPosition())
}