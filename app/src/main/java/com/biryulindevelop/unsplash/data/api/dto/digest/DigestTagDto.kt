package com.biryulindevelop.unsplash.data.api.dto.digest

import com.biryulindevelop.unsplash.domain.model.DigestTag

data class DigestTagDto(
    val title: String
) {

    fun toDigestTag(): DigestTag {
        return DigestTag(title)
    }
}
