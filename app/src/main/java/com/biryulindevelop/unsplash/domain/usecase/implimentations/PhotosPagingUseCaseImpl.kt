package com.biryulindevelop.unsplash.domain.usecase.implimentations

import androidx.paging.PagingData
import com.biryulindevelop.unsplash.data.state.Requester
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.domain.repository.PhotosPagingSourceRepository
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.PhotosPagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosPagingUseCaseImpl @Inject constructor(
    private val photosRepository: PhotosPagingSourceRepository
) : PhotosPagingUseCase {
    override fun execute(requester: Requester): Flow<PagingData<Photo>> {
        return photosRepository.getFlowPhoto(requester)
    }
}
