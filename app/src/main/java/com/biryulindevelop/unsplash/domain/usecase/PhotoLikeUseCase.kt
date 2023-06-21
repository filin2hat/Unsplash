package com.biryulindevelop.unsplash.domain.usecase

import com.biryulindevelop.unsplash.domain.model.Photo

interface PhotoLikeUseCase {

    suspend fun likePhoto(item: Photo)
}