package com.biryulindevelop.unsplash.domain.repository

import com.biryulindevelop.unsplash.domain.model.Digest

interface DigestRemoteRepository {

    suspend fun getDigests(page: Int): List<Digest>

    suspend fun getDigestInfo(id: String): Digest
}