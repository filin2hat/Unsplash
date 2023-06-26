package com.biryulindevelop.unsplash.data.repository

import com.biryulindevelop.unsplash.data.api.ApiDigest
import com.biryulindevelop.unsplash.data.utils.toListDigest
import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.domain.repository.DigestRemoteRepository
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
