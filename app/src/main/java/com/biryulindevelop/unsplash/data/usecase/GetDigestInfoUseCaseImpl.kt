package com.biryulindevelop.unsplash.data.usecase

import com.biryulindevelop.unsplash.domain.repository.DigestRemoteRepository
import com.biryulindevelop.unsplash.domain.usecase.GetDigestInfoUseCase
import javax.inject.Inject

class GetDigestInfoUseCaseImpl @Inject constructor(
    private val digestRemoteRepository: DigestRemoteRepository
) : GetDigestInfoUseCase {

    override suspend fun getDigestInfo(id: String) = digestRemoteRepository.getDigestInfo(id)
}