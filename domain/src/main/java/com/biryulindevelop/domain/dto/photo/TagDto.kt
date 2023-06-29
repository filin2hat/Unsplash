package com.biryulindevelop.domain.dto.photo

import com.biryulindevelop.domain.model.Tags

data class TagDto(
    val title: String?
) {
    fun toPhotoDetailsTags(): Tags {
        return Tags(title)
    }
}