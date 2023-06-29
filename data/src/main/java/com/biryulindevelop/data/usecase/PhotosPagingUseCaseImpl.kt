package com.biryulindevelop.data.usecase

import androidx.paging.PagingData
import com.biryulindevelop.domain.model.Photo
import com.biryulindevelop.domain.repository.PhotosPagingSourceRepository
import com.biryulindevelop.domain.state.Requester
import com.biryulindevelop.domain.usecase.PhotosPagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosPagingUseCaseImpl @Inject constructor(
    private val photosRepository: PhotosPagingSourceRepository
) : PhotosPagingUseCase {
    override fun execute(requester: Requester): Flow<PagingData<Photo>> {
        return photosRepository.getFlowPhoto(requester)
    }
}
