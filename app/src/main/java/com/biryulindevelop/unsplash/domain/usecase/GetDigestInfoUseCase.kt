package com.biryulindevelop.unsplash.domain.usecase

import com.biryulindevelop.unsplash.domain.model.Digest

interface GetDigestInfoUseCase {
    suspend fun execute(id: String): Digest
}