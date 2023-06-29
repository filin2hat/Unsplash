package com.biryulindevelop.data.usecase

import com.biryulindevelop.domain.model.Digest
import com.biryulindevelop.domain.repository.DigestRemoteRepository
import com.biryulindevelop.domain.usecase.GetDigestInfoUseCase
import javax.inject.Inject

class GetDigestInfoUseCaseImpl @Inject constructor(
    private val digestRemoteRepository: DigestRemoteRepository
) : GetDigestInfoUseCase {
    override suspend fun execute(id: String): Digest {
        return digestRemoteRepository.getDigestInfo(id)
    }
}