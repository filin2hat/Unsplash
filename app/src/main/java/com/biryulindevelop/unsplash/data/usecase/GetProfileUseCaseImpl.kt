package com.biryulindevelop.unsplash.data.usecase

import com.biryulindevelop.unsplash.domain.repository.ProfileRemoteRepository
import com.biryulindevelop.unsplash.domain.usecase.GetProfileUseCase
import javax.inject.Inject

class GetProfileUseCaseImpl @Inject constructor(
    private val profileRemoteRepository: ProfileRemoteRepository
) : GetProfileUseCase {

    override suspend fun getProfile() = profileRemoteRepository.getProfile()
}