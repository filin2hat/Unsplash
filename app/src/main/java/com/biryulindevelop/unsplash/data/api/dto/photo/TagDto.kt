package com.biryulindevelop.unsplash.data.api.dto.photo

import com.biryulindevelop.unsplash.domain.model.Tags

data class TagDto(
    val title: String?
) {
    fun toPhotoDetailsTags(): Tags {
        return Tags(title)
    }
}