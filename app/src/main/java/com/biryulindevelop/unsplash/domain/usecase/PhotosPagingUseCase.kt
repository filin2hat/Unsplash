package com.biryulindevelop.unsplash.domain.usecase

import androidx.paging.PagingData
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.domain.state.Requester
import kotlinx.coroutines.flow.Flow

interface PhotosPagingUseCase {
    fun execute(requester: Requester): Flow<PagingData<Photo>>
}
