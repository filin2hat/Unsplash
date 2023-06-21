package com.biryulindevelop.unsplash.data.api.photodto

import com.biryulindevelop.unsplash.domain.model.Tags

data class TagDto(
    val title: String?
) {
    fun toPhotoDetailsTags() = Tags(title)
}