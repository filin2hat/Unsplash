package com.biryulindevelop.unsplash.domain.usecase.implimentations

import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.domain.repository.PhotosPagingSourceRepository
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.PhotoLikeUseCase
import javax.inject.Inject

class PhotoLikeUseCaseImpl @Inject constructor(
    private val repository: PhotosPagingSourceRepository
) : PhotoLikeUseCase {
    override suspend fun execute(item: Photo) {
        val response = if (item.likedByUser) repository.deleteLike(item.id).photo
        else repository.setLike(item.id).photo
        val newItem =
            item.copy(likedByUser = response.likedByUser, likes = response.likes)
        repository.updateLikeDB(newItem.toPhotoEntity())
    }
}