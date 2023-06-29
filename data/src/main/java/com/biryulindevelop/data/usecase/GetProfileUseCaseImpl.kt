package com.biryulindevelop.data.usecase

import com.biryulindevelop.domain.model.Profile
import com.biryulindevelop.domain.repository.ProfileRemoteRepository
import com.biryulindevelop.domain.usecase.GetProfileUseCase
import javax.inject.Inject

class GetProfileUseCaseImpl @Inject constructor(
    private val profileRemoteRepository: ProfileRemoteRepository
) : GetProfileUseCase {
    override suspend fun execute(): Profile {
        return profileRemoteRepository.getProfile()
    }
}