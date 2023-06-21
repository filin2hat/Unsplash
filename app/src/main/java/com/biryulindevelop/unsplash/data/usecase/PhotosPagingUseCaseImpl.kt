package com.biryulindevelop.unsplash.data.usecase

import com.biryulindevelop.unsplash.data.state.Requester
import com.biryulindevelop.unsplash.domain.repository.PhotosPagingSourceRepository
import com.biryulindevelop.unsplash.domain.usecase.PhotosPagingUseCase
import javax.inject.Inject

class PhotosPagingUseCaseImpl @Inject constructor(
    private val photosRepository: PhotosPagingSourceRepository
) : PhotosPagingUseCase {
    override fun getPhoto(requester: Requester) =
        photosRepository.getFlowPhoto(requester)

}
