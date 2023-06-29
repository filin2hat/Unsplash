package com.biryulindevelop.domain.usecase

import com.biryulindevelop.domain.model.Photo

interface PhotoLikeUseCase {
    suspend fun execute(item: Photo)
}