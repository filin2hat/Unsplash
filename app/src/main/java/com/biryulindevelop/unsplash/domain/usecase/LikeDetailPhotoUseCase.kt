package com.biryulindevelop.unsplash.domain.usecase

import com.biryulindevelop.unsplash.domain.model.PhotoDetails

interface LikeDetailPhotoUseCase {

    suspend fun likeDetailPhoto(item: PhotoDetails)

}