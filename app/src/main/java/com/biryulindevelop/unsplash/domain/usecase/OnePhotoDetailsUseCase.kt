package com.biryulindevelop.unsplash.domain.usecase

import com.biryulindevelop.unsplash.domain.model.PhotoDetails

interface OnePhotoDetailsUseCase {

    suspend fun getPhotoDetails(id: String): PhotoDetails

}