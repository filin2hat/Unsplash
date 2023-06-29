package com.biryulindevelop.unsplash.domain.usecase

import com.biryulindevelop.unsplash.domain.model.Photo

interface PhotoLikeUseCase {
    suspend fun execute(item: Photo)
}