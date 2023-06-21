package com.biryulindevelop.unsplash.domain.repository

import com.biryulindevelop.unsplash.domain.model.Profile

interface ProfileRemoteRepository {

    suspend fun getProfile(): Profile
}