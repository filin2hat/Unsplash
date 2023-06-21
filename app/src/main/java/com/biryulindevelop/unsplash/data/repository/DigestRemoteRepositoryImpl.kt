package com.biryulindevelop.unsplash.data.repository

import com.biryulindevelop.unsplash.data.api.ApiDigest
import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.domain.repository.DigestRemoteRepository
import com.biryulindevelop.unsplash.tools.toListDigest
import javax.inject.Inject

class DigestRemoteRepositoryImpl @Inject constructor(private val apiDigest: ApiDigest) :
    DigestRemoteRepository {

    override suspend fun getDigests(page: Int): List<Digest> =
        apiDigest.getDigests(page).toListDigest()

    override suspend fun getDigestInfo(id: String): Digest = apiDigest.getDigestInfo(id).toDigest()

}