package com.biryulindevelop.unsplash.data.repository

import com.biryulindevelop.unsplash.data.api.ApiProfile
import com.biryulindevelop.unsplash.domain.model.Profile
import com.biryulindevelop.unsplash.domain.repository.ProfileRemoteRepository
import javax.inject.Inject

class ProfileRemoteRepositoryImpl @Inject constructor(private val apiProfile: ApiProfile) :
    ProfileRemoteRepository {

    override suspend fun getProfile(): Profile = apiProfile.getProfile().toProfile()
}