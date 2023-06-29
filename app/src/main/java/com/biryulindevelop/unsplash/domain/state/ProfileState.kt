package com.biryulindevelop.unsplash.domain.state

import com.biryulindevelop.unsplash.domain.model.Profile

sealed class ProfileState {
    data class Success(
        val data: Profile
    ) : ProfileState()

    object NotStarted : ProfileState()
}
