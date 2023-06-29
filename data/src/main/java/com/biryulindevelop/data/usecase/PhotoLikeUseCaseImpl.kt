package com.biryulindevelop.data.usecase

import com.biryulindevelop.domain.entity.PhotoEntity
import com.biryulindevelop.domain.model.Photo
import com.biryulindevelop.domain.repository.PhotosPagingSourceRepository
import com.biryulindevelop.domain.usecase.PhotoLikeUseCase
import javax.inject.Inject

class PhotoLikeUseCaseImpl @Inject constructor(
    private val repository: PhotosPagingSourceRepository
) : PhotoLikeUseCase {
    override suspend fun execute(item: Photo) {
        val response = if (item.likedByUser) {
            repository.deleteLike(item.id).photo
        } else {
            repository.setLike(item.id).photo
        }
        val newItem = item.copy(
            likedByUser = response.likedByUser,
            likes = response.likes
        )
        val photoEntity = PhotoEntity.fromPhoto(newItem)
        repository.updateLikeDB(photoEntity)
    }
}