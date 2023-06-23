package com.biryulindevelop.unsplash.utils

import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.data.api.dto.digest.DigestDto
import com.biryulindevelop.unsplash.data.api.dto.photo.TagDto
import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.domain.model.DigestTag
import com.biryulindevelop.unsplash.domain.model.Tags
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

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

fun SearchView.setChangeTextListener(block: (query: String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            block(newText)
            return false
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }
    })
}

fun ImageView.loadImage(urls: String) {
    Glide.with(this)
        .load(urls)
        .error(R.drawable.baseline_error_24)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.placeholder)
        .into(this)
}
