package com.biryulindevelop.unsplash.domain.usecase

import com.biryulindevelop.unsplash.domain.model.Profile

interface GetProfileUseCase {
    suspend fun execute(): Profile
}