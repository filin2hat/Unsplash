package com.biryulindevelop.data.usecase

import com.biryulindevelop.domain.model.PhotoDetails
import com.biryulindevelop.domain.repository.PhotoRemoteRepository
import com.biryulindevelop.domain.usecase.OnePhotoDetailsUseCase
import javax.inject.Inject

class OnePhotoDetailsUseCaseImpl @Inject constructor(
    private val repository: PhotoRemoteRepository
) : OnePhotoDetailsUseCase {
    override suspend fun execute(id: String): PhotoDetails {
        return repository.getPhotoDetails(id).toPhotoDetails()
    }
}