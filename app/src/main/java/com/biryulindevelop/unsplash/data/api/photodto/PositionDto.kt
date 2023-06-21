package com.biryulindevelop.unsplash.data.api.photodto

import com.biryulindevelop.unsplash.domain.model.Position

data class PositionDto(
    val latitude: Double?,
    val longitude: Double?
) {
    fun toPhotoDetailsPosition() = Position(latitude, longitude)
}