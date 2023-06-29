package com.biryulindevelop.data.usecase

import com.biryulindevelop.domain.model.PhotoDetails
import com.biryulindevelop.domain.repository.PhotosPagingSourceRepository
import com.biryulindevelop.domain.usecase.LikeDetailPhotoUseCase
import javax.inject.Inject

class LikeDetailPhotoUseCaseImpl @Inject constructor(
    private val repository: PhotosPagingSourceRepository
) : LikeDetailPhotoUseCase {
    override suspend fun execute(item: PhotoDetails) {
        if (item.likedByUser) repository.deleteLike(item.id).photo
        else repository.setLike(item.id).photo
    }
}