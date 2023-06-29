package com.biryulindevelop.domain.repository

import com.biryulindevelop.domain.model.Digest

interface DigestRemoteRepository {

    suspend fun getDigests(page: Int): List<Digest>

    suspend fun getDigestInfo(id: String): Digest
}