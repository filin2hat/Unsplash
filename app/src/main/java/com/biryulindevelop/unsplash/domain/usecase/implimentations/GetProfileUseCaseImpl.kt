package com.biryulindevelop.unsplash.domain.usecase.implimentations

import com.biryulindevelop.unsplash.domain.model.Profile
import com.biryulindevelop.unsplash.domain.repository.ProfileRemoteRepository
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.GetProfileUseCase
import javax.inject.Inject

class GetProfileUseCaseImpl @Inject constructor(
    private val profileRemoteRepository: ProfileRemoteRepository
) : GetProfileUseCase {
    override suspend fun execute(): Profile {
        return profileRemoteRepository.getProfile()
    }
}