package com.biryulindevelop.unsplash.domain.usecase.interfaceces

import com.biryulindevelop.unsplash.domain.model.Digest

interface GetDigestInfoUseCase {
    suspend fun execute(id: String): Digest
}