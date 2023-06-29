package com.biryulindevelop.domain.state

import com.biryulindevelop.domain.model.Profile

sealed class ProfileState {
    data class Success(
        val data: Profile
    ) : ProfileState()

    object NotStarted : ProfileState()
}
