package com.biryulindevelop.domain.usecase

import com.biryulindevelop.domain.model.Digest

interface GetDigestInfoUseCase {
    suspend fun execute(id: String): Digest
}