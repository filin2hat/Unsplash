package com.biryulindevelop.unsplash.domain.usecase.interfaceces

import com.biryulindevelop.unsplash.domain.model.PhotoDetails

interface LikeDetailPhotoUseCase {
    suspend fun execute(item: PhotoDetails)

}