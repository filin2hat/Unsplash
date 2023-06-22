package com.biryulindevelop.unsplash.domain.usecase.interfaceces

import com.biryulindevelop.unsplash.domain.model.PhotoDetails

interface OnePhotoDetailsUseCase {
    suspend fun execute(id: String): PhotoDetails
}