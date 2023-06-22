package com.biryulindevelop.unsplash.domain.usecase.interfaceces

import androidx.paging.PagingData
import com.biryulindevelop.unsplash.data.state.Requester
import com.biryulindevelop.unsplash.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosPagingUseCase {
    fun execute(requester: Requester): Flow<PagingData<Photo>>
}
