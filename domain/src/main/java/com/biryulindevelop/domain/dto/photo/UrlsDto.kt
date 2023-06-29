package com.biryulindevelop.domain.dto.photo

import com.biryulindevelop.domain.model.Urls

data class UrlsDto(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val smallS3: String,
    val thumb: String
) {
    fun toPhotoDetailsUrls(): Urls {
        return Urls(raw, regular)
    }
}