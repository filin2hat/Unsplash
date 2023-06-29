package com.biryulindevelop.data.repository

import com.biryulindevelop.data.api.ApiProfile
import com.biryulindevelop.domain.model.Profile
import com.biryulindevelop.domain.repository.ProfileRemoteRepository
import javax.inject.Inject

class ProfileRemoteRepositoryImpl @Inject constructor(
    private val apiProfile: ApiProfile
) : ProfileRemoteRepository {

    override suspend fun getProfile(): Profile {
        return apiProfile.getProfile().toProfile()
    }
}
