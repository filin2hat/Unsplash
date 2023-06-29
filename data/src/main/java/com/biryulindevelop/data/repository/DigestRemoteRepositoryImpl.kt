package com.biryulindevelop.data.repository

import com.biryulindevelop.data.api.ApiDigest
import com.biryulindevelop.domain.mapper.toListDigest
import com.biryulindevelop.domain.model.Digest
import com.biryulindevelop.domain.repository.DigestRemoteRepository
import javax.inject.Inject

class DigestRemoteRepositoryImpl @Inject constructor(private val apiDigest: ApiDigest) :
    DigestRemoteRepository {
    override suspend fun getDigests(page: Int): List<Digest> {
        return apiDigest.getDigests(page).toListDigest()
    }

    override suspend fun getDigestInfo(id: String): Digest {
        return apiDigest.getDigestInfo(id).toDigest()
    }
}
