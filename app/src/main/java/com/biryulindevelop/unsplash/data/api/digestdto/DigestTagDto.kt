package com.biryulindevelop.unsplash.data.api.digestdto

import com.biryulindevelop.unsplash.domain.model.DigestTag

data class DigestTagDto(
    val title: String
) {

    fun toDigestTag() = DigestTag(title)
}
