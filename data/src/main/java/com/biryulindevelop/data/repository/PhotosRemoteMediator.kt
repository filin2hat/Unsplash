package com.biryulindevelop.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.biryulindevelop.domain.entity.PhotoEntity
import com.biryulindevelop.domain.repository.LocalRepository
import com.biryulindevelop.domain.repository.PhotoRemoteRepository
import com.biryulindevelop.domain.state.Requester
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PhotosRemoteMediator @Inject constructor(
    private val localRepository: LocalRepository,
    private val photoRemoteRepository: PhotoRemoteRepository,
    private val requester: Requester
) : RemoteMediator<Int, PhotoEntity>() {

    private var pageIndex = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>,
    ): MediatorResult {
        pageIndex = getIndex(loadType) ?: return MediatorResult.Success(true)
        return try {
            val response = photoRemoteRepository.getPhotoList(requester, pageIndex).toListEntity()
            if (loadType == LoadType.REFRESH) localRepository.refresh(response)
            else localRepository.insertData(response)
            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getIndex(loadType: LoadType): Int? {
        return when (loadType) {
            LoadType.PREPEND -> null
            LoadType.REFRESH -> 0
            LoadType.APPEND -> ++pageIndex
        }
    }
}
