package com.biryulindevelop.unsplash.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.biryulindevelop.unsplash.data.api.dto.photo.WrapperPhotoDto
import com.biryulindevelop.unsplash.data.db.entity.PhotoEntity
import com.biryulindevelop.unsplash.data.state.Requester
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.domain.repository.LocalRepository
import com.biryulindevelop.unsplash.domain.repository.PhotoRemoteRepository
import com.biryulindevelop.unsplash.domain.repository.PhotosPagingSourceRepository
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
