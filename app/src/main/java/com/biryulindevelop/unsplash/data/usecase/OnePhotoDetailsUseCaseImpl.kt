package com.biryulindevelop.unsplash.data.usecase

import com.biryulindevelop.unsplash.domain.repository.PhotoRemoteRepository
import com.biryulindevelop.unsplash.domain.usecase.OnePhotoDetailsUseCase
import javax.inject.Inject

class OnePhotoDetailsUseCaseImpl @Inject constructor(private val repository: PhotoRemoteRepository) :
    OnePhotoDetailsUseCase {

    override suspend fun getPhotoDetails(id: String) =
        repository.getPhotoDetails(id).toPhotoDetails()

}