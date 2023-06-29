package com.biryulindevelop.domain.usecase

import com.biryulindevelop.domain.model.PhotoDetails

interface OnePhotoDetailsUseCase {
    suspend fun execute(id: String): PhotoDetails
}