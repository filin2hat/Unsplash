package com.biryulindevelop.domain.usecase

import com.biryulindevelop.domain.model.PhotoDetails

interface LikeDetailPhotoUseCase {
    suspend fun execute(item: PhotoDetails)
}