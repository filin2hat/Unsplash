package com.biryulindevelop.domain.usecase

import androidx.paging.PagingData
import com.biryulindevelop.domain.model.Photo
import com.biryulindevelop.domain.state.Requester
import kotlinx.coroutines.flow.Flow

interface PhotosPagingUseCase {
    fun execute(requester: Requester): Flow<PagingData<Photo>>
}
