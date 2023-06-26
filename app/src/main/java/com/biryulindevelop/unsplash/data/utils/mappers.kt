package com.biryulindevelop.unsplash.data.utils

import com.biryulindevelop.unsplash.data.api.dto.digest.DigestDto
import com.biryulindevelop.unsplash.data.api.dto.photo.TagDto
import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.domain.model.DigestTag
import com.biryulindevelop.unsplash.domain.model.Tags

inline fun <reified T, R> List<T>.toList(converter: (T) -> R): List<R> {
    return this.map { item -> converter(item) }
}

fun List<TagDto>.toListTag(): List<Tags> {
    return this.toList { it.toPhotoDetailsTags() }
}

fun List<DigestDto>.toListDigest(): List<Digest> {
    return this.toList { it.toDigest() }
}

fun List<DigestDto.DigestTagDto>.toListDigestTag(): List<DigestTag> {
    return this.toList { it.toDigestTag() }
}