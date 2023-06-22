package com.biryulindevelop.unsplash.domain.usecase.interfaceces

import com.biryulindevelop.unsplash.domain.model.Profile

interface GetProfileUseCase {
    suspend fun execute(): Profile
}