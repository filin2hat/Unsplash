package com.biryulindevelop.unsplash.data.usecase

import com.biryulindevelop.unsplash.domain.model.PhotoDetails
import com.biryulindevelop.unsplash.domain.repository.PhotosPagingSourceRepository
import com.biryulindevelop.unsplash.domain.usecase.LikeDetailPhotoUseCase
import javax.inject.Inject

class LikeDetailPhotoUseCaseImpl @Inject constructor(private val repository: PhotosPagingSourceRepository) :
    LikeDetailPhotoUseCase {

    override suspend fun likeDetailPhoto(item: PhotoDetails) {
        if (item.likedByUser) repository.deleteLike(item.id).photo
        else repository.setLike(item.id).photo
    }
}