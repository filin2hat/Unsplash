package com.biryulindevelop.domain.repository

import com.biryulindevelop.domain.model.Profile

interface ProfileRemoteRepository {
    suspend fun getProfile(): Profile
}