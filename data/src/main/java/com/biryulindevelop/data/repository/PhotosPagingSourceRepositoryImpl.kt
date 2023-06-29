package com.biryulindevelop.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.biryulindevelop.domain.dto.photo.WrapperPhotoDto
import com.biryulindevelop.domain.entity.PhotoEntity
import com.biryulindevelop.domain.model.Photo
import com.biryulindevelop.domain.repository.LocalRepository
import com.biryulindevelop.domain.repository.PhotoRemoteRepository
import com.biryulindevelop.domain.repository.PhotosPagingSourceRepository
import com.biryulindevelop.domain.state.Requester
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PhotosPagingSourceRepositoryImpl @Inject constructor(
    private val photoRemoteRepository: PhotoRemoteRepository,
    private val localRepository: LocalRepository
) : PhotosPagingSourceRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getFlowPhoto(requester: Requester): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 13
            ),
            remoteMediator = PhotosRemoteMediator(
                localRepository,
                photoRemoteRepository,
                requester
            ),
            pagingSourceFactory = { localRepository.getPagingData() }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                PhotoEntity.toPhoto(entity)
            }
        }
    }

    override suspend fun setLike(id: String): WrapperPhotoDto {
        return photoRemoteRepository.likePhoto(id)
    }

    override suspend fun deleteLike(id: String): WrapperPhotoDto {
        return photoRemoteRepository.unlikePhoto(id)
    }

    override suspend fun updateLikeDB(entity: PhotoEntity) {
        localRepository.setLikeInDataBase(entity)
    }
}
