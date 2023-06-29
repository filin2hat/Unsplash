package com.biryulindevelop.unsplash.data.repository

import androidx.paging.PagingSource
import com.biryulindevelop.unsplash.data.db.PhotosDao
import com.biryulindevelop.unsplash.domain.entity.PhotoEntity
import com.biryulindevelop.unsplash.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val photosDao: PhotosDao
) : LocalRepository {

    override suspend fun insertData(data: List<PhotoEntity>) {
        photosDao.insert(data)
    }

    override fun getPagingData(): PagingSource<Int, PhotoEntity> {
        return photosDao.getPhotos()
    }

    override suspend fun clear() {
        photosDao.deleteAll()
    }

    override suspend fun setLikeInDataBase(photoEntity: PhotoEntity) {
        photosDao.updateLocalLikes(photoEntity)
    }

    override suspend fun refresh(data: List<PhotoEntity>) {
        photosDao.refresh(data)
    }
}