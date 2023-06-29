package com.biryulindevelop.domain.usecase

import com.biryulindevelop.domain.model.Profile

interface GetProfileUseCase {
    suspend fun execute(): Profile
}